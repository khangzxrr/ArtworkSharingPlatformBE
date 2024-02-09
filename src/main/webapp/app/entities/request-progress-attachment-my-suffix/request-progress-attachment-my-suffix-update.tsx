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
import { IRequestProgressMySuffix } from 'app/shared/model/request-progress-my-suffix.model';
import { getEntities as getRequestProgresses } from 'app/entities/request-progress-my-suffix/request-progress-my-suffix.reducer';
import { IRequestProgressAttachmentMySuffix } from 'app/shared/model/request-progress-attachment-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './request-progress-attachment-my-suffix.reducer';

export const RequestProgressAttachmentMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const media = useAppSelector(state => state.media.entities);
  const requestProgresses = useAppSelector(state => state.requestProgress.entities);
  const requestProgressAttachmentEntity = useAppSelector(state => state.requestProgressAttachment.entity);
  const loading = useAppSelector(state => state.requestProgressAttachment.loading);
  const updating = useAppSelector(state => state.requestProgressAttachment.updating);
  const updateSuccess = useAppSelector(state => state.requestProgressAttachment.updateSuccess);

  const handleClose = () => {
    navigate('/request-progress-attachment-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedia({}));
    dispatch(getRequestProgresses({}));
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
      ...requestProgressAttachmentEntity,
      ...values,
      media: media.find(it => it.id.toString() === values.media.toString()),
      requestProgress: requestProgresses.find(it => it.id.toString() === values.requestProgress.toString()),
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
          ...requestProgressAttachmentEntity,
          media: requestProgressAttachmentEntity?.media?.id,
          requestProgress: requestProgressAttachmentEntity?.requestProgress?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="artworkSharingPlatformJhipterApp.requestProgressAttachment.home.createOrEditLabel"
            data-cy="RequestProgressAttachmentCreateUpdateHeading"
          >
            Create or edit a Request Progress Attachment
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="request-progress-attachment-my-suffix-id"
                  label="Id"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField id="request-progress-attachment-my-suffix-media" name="media" data-cy="media" label="Media" type="select">
                <option value="" key="0" />
                {media
                  ? media.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="request-progress-attachment-my-suffix-requestProgress"
                name="requestProgress"
                data-cy="requestProgress"
                label="Request Progress"
                type="select"
              >
                <option value="" key="0" />
                {requestProgresses
                  ? requestProgresses.map(otherEntity => (
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
                to="/request-progress-attachment-my-suffix"
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

export default RequestProgressAttachmentMySuffixUpdate;
