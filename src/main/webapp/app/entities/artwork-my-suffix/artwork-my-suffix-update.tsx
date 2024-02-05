import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArtworkSellingMySuffix } from 'app/shared/model/artwork-selling-my-suffix.model';
import { getEntities as getArtworkSellings } from 'app/entities/artwork-selling-my-suffix/artwork-selling-my-suffix.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IArtworkCategoryMySuffix } from 'app/shared/model/artwork-category-my-suffix.model';
import { getEntities as getArtworkCategories } from 'app/entities/artwork-category-my-suffix/artwork-category-my-suffix.reducer';
import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';
import { ArtworkStatus } from 'app/shared/model/enumerations/artwork-status.model';
import { getEntity, updateEntity, createEntity, reset } from './artwork-my-suffix.reducer';

export const ArtworkMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artworkSellings = useAppSelector(state => state.artworkSelling.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const artworkCategories = useAppSelector(state => state.artworkCategory.entities);
  const artworkEntity = useAppSelector(state => state.artwork.entity);
  const loading = useAppSelector(state => state.artwork.loading);
  const updating = useAppSelector(state => state.artwork.updating);
  const updateSuccess = useAppSelector(state => state.artwork.updateSuccess);
  const artworkStatusValues = Object.keys(ArtworkStatus);

  const handleClose = () => {
    navigate('/artwork-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArtworkSellings({}));
    dispatch(getUsers({}));
    dispatch(getArtworkCategories({}));
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
      ...artworkEntity,
      ...values,
      artworkSelling: artworkSellings.find(it => it.id.toString() === values.artworkSelling.toString()),
      owner: users.find(it => it.id.toString() === values.owner.toString()),
      category: artworkCategories.find(it => it.id.toString() === values.category.toString()),
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
          status: 'ENABLE',
          ...artworkEntity,
          artworkSelling: artworkEntity?.artworkSelling?.id,
          owner: artworkEntity?.owner?.id,
          category: artworkEntity?.category?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.artwork.home.createOrEditLabel" data-cy="ArtworkCreateUpdateHeading">
            Create or edit a Artwork
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
                <ValidatedField name="id" required readOnly id="artwork-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Name" id="artwork-my-suffix-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Description" id="artwork-my-suffix-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Create At" id="artwork-my-suffix-createAt" name="createAt" data-cy="createAt" type="text" />
              <ValidatedField label="Status" id="artwork-my-suffix-status" name="status" data-cy="status" type="select">
                {artworkStatusValues.map(artworkStatus => (
                  <option value={artworkStatus} key={artworkStatus}>
                    {artworkStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="artwork-my-suffix-artworkSelling"
                name="artworkSelling"
                data-cy="artworkSelling"
                label="Artwork Selling"
                type="select"
              >
                <option value="" key="0" />
                {artworkSellings
                  ? artworkSellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="artwork-my-suffix-owner" name="owner" data-cy="owner" label="Owner" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="artwork-my-suffix-category" name="category" data-cy="category" label="Category" type="select">
                <option value="" key="0" />
                {artworkCategories
                  ? artworkCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artwork-my-suffix" replace color="info">
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

export default ArtworkMySuffixUpdate;
