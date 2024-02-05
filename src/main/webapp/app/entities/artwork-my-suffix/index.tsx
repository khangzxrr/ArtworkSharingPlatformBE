import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkMySuffix from './artwork-my-suffix';
import ArtworkMySuffixDetail from './artwork-my-suffix-detail';
import ArtworkMySuffixUpdate from './artwork-my-suffix-update';
import ArtworkMySuffixDeleteDialog from './artwork-my-suffix-delete-dialog';

const ArtworkMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkMySuffix />} />
    <Route path="new" element={<ArtworkMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkMySuffixDetail />} />
      <Route path="edit" element={<ArtworkMySuffixUpdate />} />
      <Route path="delete" element={<ArtworkMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkMySuffixRoutes;
