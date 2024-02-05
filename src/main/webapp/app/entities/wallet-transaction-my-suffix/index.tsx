import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WalletTransactionMySuffix from './wallet-transaction-my-suffix';
import WalletTransactionMySuffixDetail from './wallet-transaction-my-suffix-detail';
import WalletTransactionMySuffixUpdate from './wallet-transaction-my-suffix-update';
import WalletTransactionMySuffixDeleteDialog from './wallet-transaction-my-suffix-delete-dialog';

const WalletTransactionMySuffixRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WalletTransactionMySuffix />} />
    <Route path="new" element={<WalletTransactionMySuffixUpdate />} />
    <Route path=":id">
      <Route index element={<WalletTransactionMySuffixDetail />} />
      <Route path="edit" element={<WalletTransactionMySuffixUpdate />} />
      <Route path="delete" element={<WalletTransactionMySuffixDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WalletTransactionMySuffixRoutes;
