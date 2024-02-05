import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestAttachmentMySuffix from './request-attachment-my-suffix';
import RequestAttachmentMySuffixDetail from './request-attachment-my-suffix-detail';
import RequestAttachmentMySuffixUpdate from './request-attachment-my-suffix-update';
import RequestAttachmentMySuffixDeleteDialog from './request-attachment-my-suffix-delete-dialog';

const RequestAttachmentMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestAttachmentMySuffix />} />
    <Route path="new" element={<RequestAttachmentMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<RequestAttachmentMySuffixDetail />} />
      <Route path="edit" element={<RequestAttachmentMySuffixUpdate />} />
      <Route path="delete" element={<RequestAttachmentMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequestAttachmentMySuffixRoutes;
