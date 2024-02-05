import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArtworkAssetMySuffix } from 'app/shared/model/artwork-asset-my-suffix.model';
import { getEntities as getArtworkAssets } from 'app/entities/artwork-asset-my-suffix/artwork-asset-my-suffix.reducer';
import { ICertificateMySuffix } from 'app/shared/model/certificate-my-suffix.model';
import { getEntities as getCertificates } from 'app/entities/certificate-my-suffix/certificate-my-suffix.reducer';
import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './media-my-suffix.reducer';

export const MediaMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artworkAssets = useAppSelector(state => state.artworkAsset.entities);
  const certificates = useAppSelector(state => state.certificate.entities);
  const mediaEntity = useAppSelector(state => state.media.entity);
  const loading = useAppSelector(state => state.media.loading);
  const updating = useAppSelector(state => state.media.updating);
  const updateSuccess = useAppSelector(state => state.media.updateSuccess);

  const handleClose = () => {
    navigate('/media-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArtworkAssets({}));
    dispatch(getCertificates({}));
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
      ...mediaEntity,
      ...values,
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
          ...mediaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.media.home.createOrEditLabel" data-cy="MediaCreateUpdateHeading">
            Create or edit a Media
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
                <ValidatedField name="id" required readOnly id="media-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Url" id="media-my-suffix-url" name="url" data-cy="url" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/media-my-suffix" replace color="info">
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

export default MediaMySuffixUpdate;
