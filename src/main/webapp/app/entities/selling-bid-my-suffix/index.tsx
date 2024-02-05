import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SellingBidMySuffix from './selling-bid-my-suffix';
import SellingBidMySuffixDetail from './selling-bid-my-suffix-detail';
import SellingBidMySuffixUpdate from './selling-bid-my-suffix-update';
import SellingBidMySuffixDeleteDialog from './selling-bid-my-suffix-delete-dialog';

const SellingBidMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SellingBidMySuffix />} />
    <Route path="new" element={<SellingBidMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<SellingBidMySuffixDetail />} />
      <Route path="edit" element={<SellingBidMySuffixUpdate />} />
      <Route path="delete" element={<SellingBidMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SellingBidMySuffixRoutes;
