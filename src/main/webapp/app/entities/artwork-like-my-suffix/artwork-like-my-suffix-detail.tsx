import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-like-my-suffix.reducer';

export const ArtworkLikeMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkLikeEntity = useAppSelector(state => state.artworkLike.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkLikeDetailsHeading">Artwork Like</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{artworkLikeEntity.id}</dd>
          <dt>
            <span id="createAt">Create At</span>
          </dt>
          <dd>
            {artworkLikeEntity.createAt ? (
              <TextFormat value={artworkLikeEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Owner</dt>
          <dd>{artworkLikeEntity.owner ? artworkLikeEntity.owner.id : ''}</dd>
          <dt>Artwork</dt>
          <dd>{artworkLikeEntity.artwork ? artworkLikeEntity.artwork.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artwork-like-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-like-my-suffix/${artworkLikeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkLikeMySuffixDetail;
