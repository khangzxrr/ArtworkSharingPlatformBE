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
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICertificateMySuffix } from 'app/shared/model/certificate-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './certificate-my-suffix.reducer';

export const CertificateMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const media = useAppSelector(state => state.media.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const certificateEntity = useAppSelector(state => state.certificate.entity);
  const loading = useAppSelector(state => state.certificate.loading);
  const updating = useAppSelector(state => state.certificate.updating);
  const updateSuccess = useAppSelector(state => state.certificate.updateSuccess);

  const handleClose = () => {
    navigate('/certificate-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedia({}));
    dispatch(getUsers({}));
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
      ...certificateEntity,
      ...values,
      media: media.find(it => it.id.toString() === values.media.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...certificateEntity,
          media: certificateEntity?.media?.id,
          user: certificateEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.certificate.home.createOrEditLabel" data-cy="CertificateCreateUpdateHeading">
            Create or edit a Certificate
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
                <ValidatedField name="id" required readOnly id="certificate-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Description"
                id="certificate-my-suffix-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField id="certificate-my-suffix-media" name="media" data-cy="media" label="Media" type="select">
                <option value="" key="0" />
                {media
                  ? media.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="certificate-my-suffix-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/certificate-my-suffix" replace color="info">
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

export default CertificateMySuffixUpdate;
