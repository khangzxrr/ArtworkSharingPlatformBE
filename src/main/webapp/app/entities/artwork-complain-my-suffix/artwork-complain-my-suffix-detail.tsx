import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-complain-my-suffix.reducer';

export const ArtworkComplainMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkComplainEntity = useAppSelector(state => state.artworkComplain.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkComplainDetailsHeading">Artwork Complain</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{artworkComplainEntity.id}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{artworkComplainEntity.content}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{artworkComplainEntity.status}</dd>
          <dt>User</dt>
          <dd>{artworkComplainEntity.user ? artworkComplainEntity.user.id : ''}</dd>
          <dt>Artwork</dt>
          <dd>{artworkComplainEntity.artwork ? artworkComplainEntity.artwork.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artwork-complain-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-complain-my-suffix/${artworkComplainEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkComplainMySuffixDetail;
