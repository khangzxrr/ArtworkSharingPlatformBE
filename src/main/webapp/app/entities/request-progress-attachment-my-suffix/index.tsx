import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestProgressAttachmentMySuffix from './request-progress-attachment-my-suffix';
import RequestProgressAttachmentMySuffixDetail from './request-progress-attachment-my-suffix-detail';
import RequestProgressAttachmentMySuffixUpdate from './request-progress-attachment-my-suffix-update';
import RequestProgressAttachmentMySuffixDeleteDialog from './request-progress-attachment-my-suffix-delete-dialog';

const RequestProgressAttachmentMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestProgressAttachmentMySuffix />} />
    <Route path="new" element={<RequestProgressAttachmentMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<RequestProgressAttachmentMySuffixDetail />} />
      <Route path="edit" element={<RequestProgressAttachmentMySuffixUpdate />} />
      <Route path="delete" element={<RequestProgressAttachmentMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequestProgressAttachmentMySuffixRoutes;
