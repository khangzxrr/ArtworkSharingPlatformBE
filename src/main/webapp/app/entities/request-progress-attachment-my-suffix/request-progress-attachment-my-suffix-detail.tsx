import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request-progress-attachment-my-suffix.reducer';

export const RequestProgressAttachmentMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestProgressAttachmentEntity = useAppSelector(state => state.requestProgressAttachment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestProgressAttachmentDetailsHeading">Request Progress Attachment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{requestProgressAttachmentEntity.id}</dd>
          <dt>Media</dt>
          <dd>{requestProgressAttachmentEntity.media ? requestProgressAttachmentEntity.media.id : ''}</dd>
          <dt>Request Progress</dt>
          <dd>{requestProgressAttachmentEntity.requestProgress ? requestProgressAttachmentEntity.requestProgress.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/request-progress-attachment-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-progress-attachment-my-suffix/${requestProgressAttachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestProgressAttachmentMySuffixDetail;
