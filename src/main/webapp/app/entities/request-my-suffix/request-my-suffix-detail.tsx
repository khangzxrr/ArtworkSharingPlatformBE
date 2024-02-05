import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request-my-suffix.reducer';

export const RequestMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestEntity = useAppSelector(state => state.request.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestDetailsHeading">Request</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{requestEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{requestEntity.description}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{requestEntity.status}</dd>
          <dt>User</dt>
          <dd>{requestEntity.user ? requestEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/request-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-my-suffix/${requestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestMySuffixDetail;
