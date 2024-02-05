import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWalletTransactionMySuffix } from 'app/shared/model/wallet-transaction-my-suffix.model';
import { getEntities as getWalletTransactions } from 'app/entities/wallet-transaction-my-suffix/wallet-transaction-my-suffix.reducer';
import { IRequestMySuffix } from 'app/shared/model/request-my-suffix.model';
import { getEntities as getRequests } from 'app/entities/request-my-suffix/request-my-suffix.reducer';
import { IRequestProgressMySuffix } from 'app/shared/model/request-progress-my-suffix.model';
import { RequestProgressType } from 'app/shared/model/enumerations/request-progress-type.model';
import { RequestProgressStatus } from 'app/shared/model/enumerations/request-progress-status.model';
import { getEntity, updateEntity, createEntity, reset } from './request-progress-my-suffix.reducer';

export const RequestProgressMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const walletTransactions = useAppSelector(state => state.walletTransaction.entities);
  const requests = useAppSelector(state => state.request.entities);
  const requestProgressEntity = useAppSelector(state => state.requestProgress.entity);
  const loading = useAppSelector(state => state.requestProgress.loading);
  const updating = useAppSelector(state => state.requestProgress.updating);
  const updateSuccess = useAppSelector(state => state.requestProgress.updateSuccess);
  const requestProgressTypeValues = Object.keys(RequestProgressType);
  const requestProgressStatusValues = Object.keys(RequestProgressStatus);

  const handleClose = () => {
    navigate('/request-progress-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWalletTransactions({}));
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
      ...requestProgressEntity,
      ...values,
      transaction: walletTransactions.find(it => it.id.toString() === values.transaction.toString()),
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
          type: 'FIRST_PAYMENT',
          status: 'SUCCEED',
          ...requestProgressEntity,
          transaction: requestProgressEntity?.transaction?.id,
          request: requestProgressEntity?.request?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.requestProgress.home.createOrEditLabel" data-cy="RequestProgressCreateUpdateHeading">
            Create or edit a Request Progress
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
                <ValidatedField name="id" required readOnly id="request-progress-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Date" id="request-progress-my-suffix-date" name="date" data-cy="date" type="date" />
              <ValidatedField
                label="Description"
                id="request-progress-my-suffix-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label="Type" id="request-progress-my-suffix-type" name="type" data-cy="type" type="select">
                {requestProgressTypeValues.map(requestProgressType => (
                  <option value={requestProgressType} key={requestProgressType}>
                    {requestProgressType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Status" id="request-progress-my-suffix-status" name="status" data-cy="status" type="select">
                {requestProgressStatusValues.map(requestProgressStatus => (
                  <option value={requestProgressStatus} key={requestProgressStatus}>
                    {requestProgressStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="request-progress-my-suffix-transaction"
                name="transaction"
                data-cy="transaction"
                label="Transaction"
                type="select"
                required
              >
                <option value="" key="0" />
                {walletTransactions
                  ? walletTransactions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="request-progress-my-suffix-request" name="request" data-cy="request" label="Request" type="select">
                <option value="" key="0" />
                {requests
                  ? requests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/request-progress-my-suffix" replace color="info">
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

export default RequestProgressMySuffixUpdate;
