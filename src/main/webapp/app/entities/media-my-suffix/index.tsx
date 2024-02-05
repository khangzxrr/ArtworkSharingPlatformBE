import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MediaMySuffix from './media-my-suffix';
import MediaMySuffixDetail from './media-my-suffix-detail';
import MediaMySuffixUpdate from './media-my-suffix-update';
import MediaMySuffixDeleteDialog from './media-my-suffix-delete-dialog';

const MediaMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MediaMySuffix />} />
    <Route path="new" element={<MediaMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<MediaMySuffixDetail />} />
      <Route path="edit" element={<MediaMySuffixUpdate />} />
      <Route path="delete" element={<MediaMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MediaMySuffixRoutes;
