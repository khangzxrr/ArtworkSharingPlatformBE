import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wallet-my-suffix.reducer';

export const WalletMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const walletEntity = useAppSelector(state => state.wallet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="walletDetailsHeading">Wallet</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{walletEntity.id}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{walletEntity.amount}</dd>
          <dt>User</dt>
          <dd>{walletEntity.user ? walletEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/wallet-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wallet-my-suffix/${walletEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WalletMySuffixDetail;
