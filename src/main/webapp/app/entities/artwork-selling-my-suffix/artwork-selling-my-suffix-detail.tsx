import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-selling-my-suffix.reducer';

export const ArtworkSellingMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkSellingEntity = useAppSelector(state => state.artworkSelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkSellingDetailsHeading">Artwork Selling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{artworkSellingEntity.id}</dd>
          <dt>
            <span id="createAt">Create At</span>
          </dt>
          <dd>
            {artworkSellingEntity.createAt ? (
              <TextFormat value={artworkSellingEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{artworkSellingEntity.type}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{artworkSellingEntity.status}</dd>
          <dt>
            <span id="expectedSellingPrice">Expected Selling Price</span>
          </dt>
          <dd>{artworkSellingEntity.expectedSellingPrice}</dd>
        </dl>
        <Button tag={Link} to="/artwork-selling-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-selling-my-suffix/${artworkSellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkSellingMySuffixDetail;
