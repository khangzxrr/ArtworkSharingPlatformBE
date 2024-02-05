import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestBidMySuffix from './request-bid-my-suffix';
import RequestBidMySuffixDetail from './request-bid-my-suffix-detail';
import RequestBidMySuffixUpdate from './request-bid-my-suffix-update';
import RequestBidMySuffixDeleteDialog from './request-bid-my-suffix-delete-dialog';

const RequestBidMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestBidMySuffix />} />
    <Route path="new" element={<RequestBidMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<RequestBidMySuffixDetail />} />
      <Route path="edit" element={<RequestBidMySuffixUpdate />} />
      <Route path="delete" element={<RequestBidMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequestBidMySuffixRoutes;
