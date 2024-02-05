import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { getEntities as getMedia } from 'app/entities/media-my-suffix/media-my-suffix.reducer';
import { IRequestMySuffix } from 'app/shared/model/request-my-suffix.model';
import { getEntities as getRequests } from 'app/entities/request-my-suffix/request-my-suffix.reducer';
import { IRequestAttachmentMySuffix } from 'app/shared/model/request-attachment-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './request-attachment-my-suffix.reducer';

export const RequestAttachmentMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const media = useAppSelector(state => state.media.entities);
  const requests = useAppSelector(state => state.request.entities);
  const requestAttachmentEntity = useAppSelector(state => state.requestAttachment.entity);
  const loading = useAppSelector(state => state.requestAttachment.loading);
  const updating = useAppSelector(state => state.requestAttachment.updating);
  const updateSuccess = useAppSelector(state => state.requestAttachment.updateSuccess);

  const handleClose = () => {
    navigate('/request-attachment-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedia({}));
    dispatch(getRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...requestAttachmentEntity,
      ...values,
      media: media.find(it => it.id.toString() === values.media.toString()),
      request: requests.find(it => it.id.toString() === values.request.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...requestAttachmentEntity,
          media: requestAttachmentEntity?.media?.id,
          request: requestAttachmentEntity?.request?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.requestAttachment.home.createOrEditLabel" data-cy="RequestAttachmentCreateUpdateHeading">
            Create or edit a Request Attachment
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="request-attachment-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField id="request-attachment-my-suffix-media" name="media" data-cy="media" label="Media" type="select">
                <option value="" key="0" />
                {media
                  ? media.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="request-attachment-my-suffix-request" name="request" data-cy="request" label="Request" type="select">
                <option value="" key="0" />
                {requests
                  ? requests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/request-attachment-my-suffix"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RequestAttachmentMySuffixUpdate;
