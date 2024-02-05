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
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';
import { getEntities as getArtworks } from 'app/entities/artwork-my-suffix/artwork-my-suffix.reducer';
import { IArtworkAssetMySuffix } from 'app/shared/model/artwork-asset-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './artwork-asset-my-suffix.reducer';

export const ArtworkAssetMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const media = useAppSelector(state => state.media.entities);
  const artworks = useAppSelector(state => state.artwork.entities);
  const artworkAssetEntity = useAppSelector(state => state.artworkAsset.entity);
  const loading = useAppSelector(state => state.artworkAsset.loading);
  const updating = useAppSelector(state => state.artworkAsset.updating);
  const updateSuccess = useAppSelector(state => state.artworkAsset.updateSuccess);

  const handleClose = () => {
    navigate('/artwork-asset-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMedia({}));
    dispatch(getArtworks({}));
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
      ...artworkAssetEntity,
      ...values,
      media: media.find(it => it.id.toString() === values.media.toString()),
      artwork: artworks.find(it => it.id.toString() === values.artwork.toString()),
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
          ...artworkAssetEntity,
          media: artworkAssetEntity?.media?.id,
          artwork: artworkAssetEntity?.artwork?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.artworkAsset.home.createOrEditLabel" data-cy="ArtworkAssetCreateUpdateHeading">
            Create or edit a Artwork Asset
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
                <ValidatedField name="id" required readOnly id="artwork-asset-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField id="artwork-asset-my-suffix-media" name="media" data-cy="media" label="Media" type="select">
                <option value="" key="0" />
                {media
                  ? media.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="artwork-asset-my-suffix-artwork" name="artwork" data-cy="artwork" label="Artwork" type="select">
                <option value="" key="0" />
                {artworks
                  ? artworks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artwork-asset-my-suffix" replace color="info">
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

export default ArtworkAssetMySuffixUpdate;
