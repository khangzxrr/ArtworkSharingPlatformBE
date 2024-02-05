import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WalletMySuffix from './wallet-my-suffix';
import WalletMySuffixDetail from './wallet-my-suffix-detail';
import WalletMySuffixUpdate from './wallet-my-suffix-update';
import WalletMySuffixDeleteDialog from './wallet-my-suffix-delete-dialog';

const WalletMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WalletMySuffix />} />
    <Route path="new" element={<WalletMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<WalletMySuffixDetail />} />
      <Route path="edit" element={<WalletMySuffixUpdate />} />
      <Route path="delete" element={<WalletMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WalletMySuffixRoutes;
