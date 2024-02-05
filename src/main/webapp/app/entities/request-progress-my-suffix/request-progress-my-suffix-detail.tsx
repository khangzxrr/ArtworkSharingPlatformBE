import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request-progress-my-suffix.reducer';

export const RequestProgressMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestProgressEntity = useAppSelector(state => state.requestProgress.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestProgressDetailsHeading">Request Progress</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{requestProgressEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {requestProgressEntity.date ? (
              <TextFormat value={requestProgressEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{requestProgressEntity.description}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{requestProgressEntity.type}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{requestProgressEntity.status}</dd>
          <dt>Transaction</dt>
          <dd>{requestProgressEntity.transaction ? requestProgressEntity.transaction.id : ''}</dd>
          <dt>Request</dt>
          <dd>{requestProgressEntity.request ? requestProgressEntity.request.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/request-progress-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-progress-my-suffix/${requestProgressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestProgressMySuffixDetail;
