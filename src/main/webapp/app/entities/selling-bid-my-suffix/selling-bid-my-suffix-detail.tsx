import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './selling-bid-my-suffix.reducer';

export const SellingBidMySuffixDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sellingBidEntity = useAppSelector(state => state.sellingBid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sellingBidDetailsHeading">Selling Bid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{sellingBidEntity.id}</dd>
          <dt>
            <span id="bidPrice">Bid Price</span>
          </dt>
          <dd>{sellingBidEntity.bidPrice}</dd>
          <dt>
            <span id="createAt">Create At</span>
          </dt>
          <dd>
            {sellingBidEntity.createAt ? <TextFormat value={sellingBidEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{sellingBidEntity.status}</dd>
          <dt>Transaction</dt>
          <dd>{sellingBidEntity.transaction ? sellingBidEntity.transaction.id : ''}</dd>
          <dt>Artwork Selling</dt>
          <dd>{sellingBidEntity.artworkSelling ? sellingBidEntity.artworkSelling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/selling-bid-my-suffix" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/selling-bid-my-suffix/${sellingBidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SellingBidMySuffixDetail;
