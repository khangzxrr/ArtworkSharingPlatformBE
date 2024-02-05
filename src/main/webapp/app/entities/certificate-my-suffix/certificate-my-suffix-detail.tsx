import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './certificate-my-suffix.reducer';

export const CertificateMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const certificateEntity = useAppSelector(state => state.certificate.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="certificateDetailsHeading">Certificate</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{certificateEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{certificateEntity.description}</dd>
          <dt>Media</dt>
          <dd>{certificateEntity.media ? certificateEntity.media.id : ''}</dd>
          <dt>User</dt>
          <dd>{certificateEntity.user ? certificateEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/certificate-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/certificate-my-suffix/${certificateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CertificateMySuffixDetail;
