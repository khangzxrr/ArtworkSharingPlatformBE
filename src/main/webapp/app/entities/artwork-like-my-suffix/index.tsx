import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkLikeMySuffix from './artwork-like-my-suffix';
import ArtworkLikeMySuffixDetail from './artwork-like-my-suffix-detail';
import ArtworkLikeMySuffixUpdate from './artwork-like-my-suffix-update';
import ArtworkLikeMySuffixDeleteDialog from './artwork-like-my-suffix-delete-dialog';

const ArtworkLikeMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkLikeMySuffix />} />
    <Route path="new" element={<ArtworkLikeMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkLikeMySuffixDetail />} />
      <Route path="edit" element={<ArtworkLikeMySuffixUpdate />} />
      <Route path="delete" element={<ArtworkLikeMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkLikeMySuffixRoutes;
