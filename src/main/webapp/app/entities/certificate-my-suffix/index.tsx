import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CertificateMySuffix from './certificate-my-suffix';
import CertificateMySuffixDetail from './certificate-my-suffix-detail';
import CertificateMySuffixUpdate from './certificate-my-suffix-update';
import CertificateMySuffixDeleteDialog from './certificate-my-suffix-delete-dialog';

const CertificateMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CertificateMySuffix />} />
    <Route path="new" element={<CertificateMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<CertificateMySuffixDetail />} />
      <Route path="edit" element={<CertificateMySuffixUpdate />} />
      <Route path="delete" element={<CertificateMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CertificateMySuffixRoutes;
