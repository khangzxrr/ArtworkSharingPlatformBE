import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkAssetMySuffix from './artwork-asset-my-suffix';
import ArtworkAssetMySuffixDetail from './artwork-asset-my-suffix-detail';
import ArtworkAssetMySuffixUpdate from './artwork-asset-my-suffix-update';
import ArtworkAssetMySuffixDeleteDialog from './artwork-asset-my-suffix-delete-dialog';

const ArtworkAssetMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkAssetMySuffix />} />
    <Route path="new" element={<ArtworkAssetMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkAssetMySuffixDetail />} />
      <Route path="edit" element={<ArtworkAssetMySuffixUpdate />} />
      <Route path="delete" element={<ArtworkAssetMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkAssetMySuffixRoutes;
