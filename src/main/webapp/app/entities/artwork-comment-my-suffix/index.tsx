import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkCommentMySuffix from './artwork-comment-my-suffix';
import ArtworkCommentMySuffixDetail from './artwork-comment-my-suffix-detail';
import ArtworkCommentMySuffixUpdate from './artwork-comment-my-suffix-update';
import ArtworkCommentMySuffixDeleteDialog from './artwork-comment-my-suffix-delete-dialog';

const ArtworkCommentMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkCommentMySuffix />} />
    <Route path="new" element={<ArtworkCommentMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkCommentMySuffixDetail />} />
      <Route path="edit" element={<ArtworkCommentMySuffixUpdate />} />
      <Route path="delete" element={<ArtworkCommentMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkCommentMySuffixRoutes;
