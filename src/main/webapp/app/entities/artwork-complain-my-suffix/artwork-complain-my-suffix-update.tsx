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
import { IArtworkComplainMySuffix } from 'app/shared/model/artwork-complain-my-suffix.model';
import { ComplainStatus } from 'app/shared/model/enumerations/complain-status.model';
import { getEntity, updateEntity, createEntity, reset } from './artwork-complain-my-suffix.reducer';

export const ArtworkComplainMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const artworks = useAppSelector(state => state.artwork.entities);
  const artworkComplainEntity = useAppSelector(state => state.artworkComplain.entity);
  const loading = useAppSelector(state => state.artworkComplain.loading);
  const updating = useAppSelector(state => state.artworkComplain.updating);
  const updateSuccess = useAppSelector(state => state.artworkComplain.updateSuccess);
  const complainStatusValues = Object.keys(ComplainStatus);

  const handleClose = () => {
    navigate('/artwork-complain-my-suffix');
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
      ...artworkComplainEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          status: 'POSTED',
          ...artworkComplainEntity,
          user: artworkComplainEntity?.user?.id,
          artwork: artworkComplainEntity?.artwork?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.artworkComplain.home.createOrEditLabel" data-cy="ArtworkComplainCreateUpdateHeading">
            Create or edit a Artwork Complain
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
                <ValidatedField name="id" required readOnly id="artwork-complain-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Content" id="artwork-complain-my-suffix-content" name="content" data-cy="content" type="text" />
              <ValidatedField label="Status" id="artwork-complain-my-suffix-status" name="status" data-cy="status" type="select">
                {complainStatusValues.map(complainStatus => (
                  <option value={complainStatus} key={complainStatus}>
                    {complainStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="artwork-complain-my-suffix-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="artwork-complain-my-suffix-artwork" name="artwork" data-cy="artwork" label="Artwork" type="select">
                <option value="" key="0" />
                {artworks
                  ? artworks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artwork-complain-my-suffix" replace color="info">
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

export default ArtworkComplainMySuffixUpdate;
