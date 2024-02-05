import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkCategoryMySuffix from './artwork-category-my-suffix';
import ArtworkCategoryMySuffixDetail from './artwork-category-my-suffix-detail';
import ArtworkCategoryMySuffixUpdate from './artwork-category-my-suffix-update';
import ArtworkCategoryMySuffixDeleteDialog from './artwork-category-my-suffix-delete-dialog';

const ArtworkCategoryMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkCategoryMySuffix />} />
    <Route path="new" element={<ArtworkCategoryMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkCategoryMySuffixDetail />} />
      <Route path="edit" element={<ArtworkCategoryMySuffixUpdate />} />
      <Route path="delete" element={<ArtworkCategoryMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkCategoryMySuffixRoutes;
