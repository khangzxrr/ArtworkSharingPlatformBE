import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-category-my-suffix.reducer';

export const ArtworkCategoryMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkCategoryEntity = useAppSelector(state => state.artworkCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkCategoryDetailsHeading">Artwork Category</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{artworkCategoryEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{artworkCategoryEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/artwork-category-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-category-my-suffix/${artworkCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkCategoryMySuffixDetail;
