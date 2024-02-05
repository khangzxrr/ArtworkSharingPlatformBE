import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArtworkMySuffix } from 'app/shared/model/artwork-my-suffix.model';
import { getEntities as getArtworks } from 'app/entities/artwork-my-suffix/artwork-my-suffix.reducer';
import { IArtworkSellingMySuffix } from 'app/shared/model/artwork-selling-my-suffix.model';
import { ArtworkSellingType } from 'app/shared/model/enumerations/artwork-selling-type.model';
import { ArtworkSellingStatus } from 'app/shared/model/enumerations/artwork-selling-status.model';
import { getEntity, updateEntity, createEntity, reset } from './artwork-selling-my-suffix.reducer';

export const ArtworkSellingMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artworks = useAppSelector(state => state.artwork.entities);
  const artworkSellingEntity = useAppSelector(state => state.artworkSelling.entity);
  const loading = useAppSelector(state => state.artworkSelling.loading);
  const updating = useAppSelector(state => state.artworkSelling.updating);
  const updateSuccess = useAppSelector(state => state.artworkSelling.updateSuccess);
  const artworkSellingTypeValues = Object.keys(ArtworkSellingType);
  const artworkSellingStatusValues = Object.keys(ArtworkSellingStatus);

  const handleClose = () => {
    navigate('/artwork-selling-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    if (values.expectedSellingPrice !== undefined && typeof values.expectedSellingPrice !== 'number') {
      values.expectedSellingPrice = Number(values.expectedSellingPrice);
    }

    const entity = {
      ...artworkSellingEntity,
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
          type: 'DIRECT',
          status: 'ON_GOING',
          ...artworkSellingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.artworkSelling.home.createOrEditLabel" data-cy="ArtworkSellingCreateUpdateHeading">
            Create or edit a Artwork Selling
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
                <ValidatedField name="id" required readOnly id="artwork-selling-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Create At" id="artwork-selling-my-suffix-createAt" name="createAt" data-cy="createAt" type="date" />
              <ValidatedField label="Type" id="artwork-selling-my-suffix-type" name="type" data-cy="type" type="select">
                {artworkSellingTypeValues.map(artworkSellingType => (
                  <option value={artworkSellingType} key={artworkSellingType}>
                    {artworkSellingType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Status" id="artwork-selling-my-suffix-status" name="status" data-cy="status" type="select">
                {artworkSellingStatusValues.map(artworkSellingStatus => (
                  <option value={artworkSellingStatus} key={artworkSellingStatus}>
                    {artworkSellingStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Expected Selling Price"
                id="artwork-selling-my-suffix-expectedSellingPrice"
                name="expectedSellingPrice"
                data-cy="expectedSellingPrice"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artwork-selling-my-suffix" replace color="info">
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

export default ArtworkSellingMySuffixUpdate;
