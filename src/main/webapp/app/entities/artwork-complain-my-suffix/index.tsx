import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkComplainMySuffix from './artwork-complain-my-suffix';
import ArtworkComplainMySuffixDetail from './artwork-complain-my-suffix-detail';
import ArtworkComplainMySuffixUpdate from './artwork-complain-my-suffix-update';
import ArtworkComplainMySuffixDeleteDialog from './artwork-complain-my-suffix-delete-dialog';

const ArtworkComplainMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkComplainMySuffix />} />
    <Route path="new" element={<ArtworkComplainMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkComplainMySuffixDetail />} />
      <Route path="edit" element={<ArtworkComplainMySuffixUpdate />} />
      <Route path="delete" element={<ArtworkComplainMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkComplainMySuffixRoutes;
