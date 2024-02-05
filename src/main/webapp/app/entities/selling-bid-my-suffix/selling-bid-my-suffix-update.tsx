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
import { IArtworkSellingMySuffix } from 'app/shared/model/artwork-selling-my-suffix.model';
import { getEntities as getArtworkSellings } from 'app/entities/artwork-selling-my-suffix/artwork-selling-my-suffix.reducer';
import { ISellingBidMySuffix } from 'app/shared/model/selling-bid-my-suffix.model';
import { SellingBidStatus } from 'app/shared/model/enumerations/selling-bid-status.model';
import { getEntity, updateEntity, createEntity, reset } from './selling-bid-my-suffix.reducer';

export const SellingBidMySuffixUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const walletTransactions = useAppSelector(state => state.walletTransaction.entities);
  const artworkSellings = useAppSelector(state => state.artworkSelling.entities);
  const sellingBidEntity = useAppSelector(state => state.sellingBid.entity);
  const loading = useAppSelector(state => state.sellingBid.loading);
  const updating = useAppSelector(state => state.sellingBid.updating);
  const updateSuccess = useAppSelector(state => state.sellingBid.updateSuccess);
  const sellingBidStatusValues = Object.keys(SellingBidStatus);

  const handleClose = () => {
    navigate('/selling-bid-my-suffix');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWalletTransactions({}));
    dispatch(getArtworkSellings({}));
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
    if (values.bidPrice !== undefined && typeof values.bidPrice !== 'number') {
      values.bidPrice = Number(values.bidPrice);
    }

    const entity = {
      ...sellingBidEntity,
      ...values,
      transaction: walletTransactions.find(it => it.id.toString() === values.transaction.toString()),
      artworkSelling: artworkSellings.find(it => it.id.toString() === values.artworkSelling.toString()),
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
          ...sellingBidEntity,
          transaction: sellingBidEntity?.transaction?.id,
          artworkSelling: sellingBidEntity?.artworkSelling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="artworkSharingPlatformJhipterApp.sellingBid.home.createOrEditLabel" data-cy="SellingBidCreateUpdateHeading">
            Create or edit a Selling Bid
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
                <ValidatedField name="id" required readOnly id="selling-bid-my-suffix-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bid Price" id="selling-bid-my-suffix-bidPrice" name="bidPrice" data-cy="bidPrice" type="text" />
              <ValidatedField label="Create At" id="selling-bid-my-suffix-createAt" name="createAt" data-cy="createAt" type="date" />
              <ValidatedField label="Status" id="selling-bid-my-suffix-status" name="status" data-cy="status" type="select">
                {sellingBidStatusValues.map(sellingBidStatus => (
                  <option value={sellingBidStatus} key={sellingBidStatus}>
                    {sellingBidStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="selling-bid-my-suffix-transaction"
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
              <ValidatedField
                id="selling-bid-my-suffix-artworkSelling"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/selling-bid-my-suffix" replace color="info">
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

export default SellingBidMySuffixUpdate;
