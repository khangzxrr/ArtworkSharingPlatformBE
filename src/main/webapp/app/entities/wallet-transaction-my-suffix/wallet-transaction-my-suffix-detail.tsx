import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wallet-transaction-my-suffix.reducer';

export const WalletTransactionMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const walletTransactionEntity = useAppSelector(state => state.walletTransaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="walletTransactionDetailsHeading">Wallet Transaction</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{walletTransactionEntity.id}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{walletTransactionEntity.amount}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{walletTransactionEntity.type}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{walletTransactionEntity.status}</dd>
          <dt>
            <span id="createAt">Create At</span>
          </dt>
          <dd>
            {walletTransactionEntity.createAt ? (
              <TextFormat value={walletTransactionEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Wallet</dt>
          <dd>{walletTransactionEntity.wallet ? walletTransactionEntity.wallet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/wallet-transaction-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wallet-transaction-my-suffix/${walletTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WalletTransactionMySuffixDetail;
