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
import { IRequestMySuffix } from 'app/shared/model/request-my-suffix.model';
import { getEntities as getRequests } from 'app/entities/request-my-suffix/request-my-suffix.reducer';
import { IRequestBidMySuffix } from 'app/shared/model/request-bid-my-suffix.model';
import { RequestBidStatus } from 'app/shared/model/enumerations/request-bid-status.model';
import { getEntity, updateEntity, createEntity, reset } from './request-bid-my-suffix.reducer';

export const RequestBidMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const requests = useAppSelector(state => state.request.entities);
  const requestBidEntity = useAppSelector(state => state.requestBid.entity);
  const loading = useAppSelector(state => state.requestBid.loading);
  const updating = useAppSelector(state => state.requestBid.updating);
  const updateSuccess = useAppSelector(state => state.requestBid.updateSuccess);
  const requestBidStatusValues = Object.keys(RequestBidStatus);

  const handleClose = () => {
    navigate('/request-bid-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
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
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.deadline !== undefined && typeof values.deadline !== 'number') {
      values.deadline = Number(values.deadline);
    }

    const entity = {
      ...requestBidEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          status: 'BIDED',
          ...requestBidEntity,
          user: requestBidEntity?.user?.id,
          request: requestBidEntity?.request?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.requestBid.home.createOrEditLabel" data-cy="RequestBidCreateUpdateHeading">
            Create or edit a Request Bid
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
                <ValidatedField name="id" required readOnly id="request-bid-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Description"
                id="request-bid-my-suffix-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label="Price" id="request-bid-my-suffix-price" name="price" data-cy="price" type="text" />
              <ValidatedField label="Deadline" id="request-bid-my-suffix-deadline" name="deadline" data-cy="deadline" type="text" />
              <ValidatedField label="Status" id="request-bid-my-suffix-status" name="status" data-cy="status" type="select">
                {requestBidStatusValues.map(requestBidStatus => (
                  <option value={requestBidStatus} key={requestBidStatus}>
                    {requestBidStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="request-bid-my-suffix-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="request-bid-my-suffix-request" name="request" data-cy="request" label="Request" type="select">
                <option value="" key="0" />
                {requests
                  ? requests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/request-bid-my-suffix" replace color="info">
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

export default RequestBidMySuffixUpdate;
