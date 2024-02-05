import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WalletMySuffix from './wallet-my-suffix';
import WalletTransactionMySuffix from './wallet-transaction-my-suffix';
import ArtworkMySuffix from './artwork-my-suffix';
import ArtworkAssetMySuffix from './artwork-asset-my-suffix';
import CertificateMySuffix from './certificate-my-suffix';
import MediaMySuffix from './media-my-suffix';
import RequestMySuffix from './request-my-suffix';
import RequestBidMySuffix from './request-bid-my-suffix';
import RequestProgressMySuffix from './request-progress-my-suffix';
import ArtworkCommentMySuffix from './artwork-comment-my-suffix';
import ArtworkLikeMySuffix from './artwork-like-my-suffix';
import ArtworkCategoryMySuffix from './artwork-category-my-suffix';
import ArtworkSellingMySuffix from './artwork-selling-my-suffix';
import ArtworkComplainMySuffix from './artwork-complain-my-suffix';
import SellingBidMySuffix from './selling-bid-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="wallet-my-suffix/*" element={<WalletMySuffix />} />
        <Route path="wallet-transaction-my-suffix/*" element={<WalletTransactionMySuffix />} />
        <Route path="artwork-my-suffix/*" element={<ArtworkMySuffix />} />
        <Route path="artwork-asset-my-suffix/*" element={<ArtworkAssetMySuffix />} />
        <Route path="certificate-my-suffix/*" element={<CertificateMySuffix />} />
        <Route path="media-my-suffix/*" element={<MediaMySuffix />} />
        <Route path="request-my-suffix/*" element={<RequestMySuffix />} />
        <Route path="request-bid-my-suffix/*" element={<RequestBidMySuffix />} />
        <Route path="request-progress-my-suffix/*" element={<RequestProgressMySuffix />} />
        <Route path="artwork-comment-my-suffix/*" element={<ArtworkCommentMySuffix />} />
        <Route path="artwork-like-my-suffix/*" element={<ArtworkLikeMySuffix />} />
        <Route path="artwork-category-my-suffix/*" element={<ArtworkCategoryMySuffix />} />
        <Route path="artwork-selling-my-suffix/*" element={<ArtworkSellingMySuffix />} />
        <Route path="artwork-complain-my-suffix/*" element={<ArtworkComplainMySuffix />} />
        <Route path="selling-bid-my-suffix/*" element={<SellingBidMySuffix />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
