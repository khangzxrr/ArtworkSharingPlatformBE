import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request-attachment-my-suffix.reducer';

export const RequestAttachmentMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestAttachmentEntity = useAppSelector(state => state.requestAttachment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestAttachmentDetailsHeading">Request Attachment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{requestAttachmentEntity.id}</dd>
          <dt>Media</dt>
          <dd>{requestAttachmentEntity.media ? requestAttachmentEntity.media.id : ''}</dd>
          <dt>Request</dt>
          <dd>{requestAttachmentEntity.request ? requestAttachmentEntity.request.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/request-attachment-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-attachment-my-suffix/${requestAttachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestAttachmentMySuffixDetail;
