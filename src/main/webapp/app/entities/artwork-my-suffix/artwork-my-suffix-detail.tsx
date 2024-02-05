import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-my-suffix.reducer';

export const ArtworkMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkEntity = useAppSelector(state => state.artwork.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkDetailsHeading">Artwork</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{artworkEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{artworkEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{artworkEntity.description}</dd>
          <dt>
            <span id="createAt">Create At</span>
          </dt>
          <dd>{artworkEntity.createAt}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{artworkEntity.status}</dd>
          <dt>Artwork Selling</dt>
          <dd>{artworkEntity.artworkSelling ? artworkEntity.artworkSelling.id : ''}</dd>
          <dt>Owner</dt>
          <dd>{artworkEntity.owner ? artworkEntity.owner.id : ''}</dd>
          <dt>Category</dt>
          <dd>{artworkEntity.category ? artworkEntity.category.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artwork-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-my-suffix/${artworkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkMySuffixDetail;
