import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestMySuffix from './request-my-suffix';
import RequestMySuffixDetail from './request-my-suffix-detail';
import RequestMySuffixUpdate from './request-my-suffix-update';
import RequestMySuffixDeleteDialog from './request-my-suffix-delete-dialog';

const RequestMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestMySuffix />} />
    <Route path="new" element={<RequestMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<RequestMySuffixDetail />} />
      <Route path="edit" element={<RequestMySuffixUpdate />} />
      <Route path="delete" element={<RequestMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequestMySuffixRoutes;
