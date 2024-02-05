import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestProgressMySuffix from './request-progress-my-suffix';
import RequestProgressMySuffixDetail from './request-progress-my-suffix-detail';
import RequestProgressMySuffixUpdate from './request-progress-my-suffix-update';
import RequestProgressMySuffixDeleteDialog from './request-progress-my-suffix-delete-dialog';

const RequestProgressMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestProgressMySuffix />} />
    <Route path="new" element={<RequestProgressMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<RequestProgressMySuffixDetail />} />
      <Route path="edit" element={<RequestProgressMySuffixUpdate />} />
      <Route path="delete" element={<RequestProgressMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequestProgressMySuffixRoutes;
