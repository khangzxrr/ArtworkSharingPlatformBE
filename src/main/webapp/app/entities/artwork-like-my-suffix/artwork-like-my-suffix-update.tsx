import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';
import { getEntities as getArtworks } from 'app/entities/artwork-my-suffix/artwork-my-suffix.reducer';
import { IArtworkLikeMySuffix } from 'app/shared/model/artwork-like-my-suffix.model';
import { getEntity, updateEntity, createEntity, reset } from './artwork-like-my-suffix.reducer';

export const ArtworkLikeMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const artworks = useAppSelector(state => state.artwork.entities);
  const artworkLikeEntity = useAppSelector(state => state.artworkLike.entity);
  const loading = useAppSelector(state => state.artworkLike.loading);
  const updating = useAppSelector(state => state.artworkLike.updating);
  const updateSuccess = useAppSelector(state => state.artworkLike.updateSuccess);

  const handleClose = () => {
    navigate('/artwork-like-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
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
      ...artworkLikeEntity,
      ...values,
      owner: users.find(it => it.id.toString() === values.owner.toString()),
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
          ...artworkLikeEntity,
          owner: artworkLikeEntity?.owner?.id,
          artwork: artworkLikeEntity?.artwork?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.artworkLike.home.createOrEditLabel" data-cy="ArtworkLikeCreateUpdateHeading">
            Create or edit a Artwork Like
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
                <ValidatedField name="id" required readOnly id="artwork-like-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Create At" id="artwork-like-my-suffix-createAt" name="createAt" data-cy="createAt" type="date" />
              <ValidatedField id="artwork-like-my-suffix-owner" name="owner" data-cy="owner" label="Owner" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="artwork-like-my-suffix-artwork" name="artwork" data-cy="artwork" label="Artwork" type="select">
                <option value="" key="0" />
                {artworks
                  ? artworks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artwork-like-my-suffix" replace color="info">
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

export default ArtworkLikeMySuffixUpdate;
