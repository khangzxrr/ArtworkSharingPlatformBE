import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-asset-my-suffix.reducer';

export const ArtworkAssetMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkAssetEntity = useAppSelector(state => state.artworkAsset.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkAssetDetailsHeading">Artwork Asset</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{artworkAssetEntity.id}</dd>
          <dt>Media</dt>
          <dd>{artworkAssetEntity.media ? artworkAssetEntity.media.id : ''}</dd>
          <dt>Artwork</dt>
          <dd>{artworkAssetEntity.artwork ? artworkAssetEntity.artwork.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artwork-asset-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-asset-my-suffix/${artworkAssetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkAssetMySuffixDetail;
