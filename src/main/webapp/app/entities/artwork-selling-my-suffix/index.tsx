import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkSellingMySuffix from './artwork-selling-my-suffix';
import ArtworkSellingMySuffixDetail from './artwork-selling-my-suffix-detail';
import ArtworkSellingMySuffixUpdate from './artwork-selling-my-suffix-update';
import ArtworkSellingMySuffixDeleteDialog from './artwork-selling-my-suffix-delete-dialog';

const ArtworkSellingMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkSellingMySuffix />} />
    <Route path="new" element={<ArtworkSellingMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkSellingMySuffixDetail />} />
      <Route path="edit" element={<ArtworkSellingMySuffixUpdate />} />
      <Route path="delete" element={<ArtworkSellingMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkSellingMySuffixRoutes;
