import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request-bid-my-suffix.reducer';

export const RequestBidMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestBidEntity = useAppSelector(state => state.requestBid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestBidDetailsHeading">Request Bid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{requestBidEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{requestBidEntity.description}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{requestBidEntity.price}</dd>
          <dt>
            <span id="duration">Duration</span>
          </dt>
          <dd>{requestBidEntity.duration}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{requestBidEntity.status}</dd>
          <dt>User</dt>
          <dd>{requestBidEntity.user ? requestBidEntity.user.id : ''}</dd>
          <dt>Request</dt>
          <dd>{requestBidEntity.request ? requestBidEntity.request.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/request-bid-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-bid-my-suffix/${requestBidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestBidMySuffixDetail;
