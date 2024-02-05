import wallet from 'app/entities/wallet-my-suffix/wallet-my-suffix.reducer';
import walletTransaction from 'app/entities/wallet-transaction-my-suffix/wallet-transaction-my-suffix.reducer';
import artwork from 'app/entities/artwork-my-suffix/artwork-my-suffix.reducer';
import artworkAsset from 'app/entities/artwork-asset-my-suffix/artwork-asset-my-suffix.reducer';
import certificate from 'app/entities/certificate-my-suffix/certificate-my-suffix.reducer';
import media from 'app/entities/media-my-suffix/media-my-suffix.reducer';
import request from 'app/entities/request-my-suffix/request-my-suffix.reducer';
import requestBid from 'app/entities/request-bid-my-suffix/request-bid-my-suffix.reducer';
import requestProgress from 'app/entities/request-progress-my-suffix/request-progress-my-suffix.reducer';
import artworkComment from 'app/entities/artwork-comment-my-suffix/artwork-comment-my-suffix.reducer';
import artworkLike from 'app/entities/artwork-like-my-suffix/artwork-like-my-suffix.reducer';
import artworkCategory from 'app/entities/artwork-category-my-suffix/artwork-category-my-suffix.reducer';
import artworkSelling from 'app/entities/artwork-selling-my-suffix/artwork-selling-my-suffix.reducer';
import artworkComplain from 'app/entities/artwork-complain-my-suffix/artwork-complain-my-suffix.reducer';
import sellingBid from 'app/entities/selling-bid-my-suffix/selling-bid-my-suffix.reducer';
import requestAttachment from 'app/entities/request-attachment-my-suffix/request-attachment-my-suffix.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  wallet,
  walletTransaction,
  artwork,
  artworkAsset,
  certificate,
  media,
  request,
  requestBid,
  requestProgress,
  artworkComment,
  artworkLike,
  artworkCategory,
  artworkSelling,
  artworkComplain,
  sellingBid,
  requestAttachment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
