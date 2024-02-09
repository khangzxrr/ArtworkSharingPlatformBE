import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/wallet-my-suffix">
        Wallet
      </MenuItem>
      <MenuItem icon="asterisk" to="/wallet-transaction-my-suffix">
        Wallet Transaction
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-my-suffix">
        Artwork
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-asset-my-suffix">
        Artwork Asset
      </MenuItem>
      <MenuItem icon="asterisk" to="/certificate-my-suffix">
        Certificate
      </MenuItem>
      <MenuItem icon="asterisk" to="/media-my-suffix">
        Media
      </MenuItem>
      <MenuItem icon="asterisk" to="/request-my-suffix">
        Request
      </MenuItem>
      <MenuItem icon="asterisk" to="/request-bid-my-suffix">
        Request Bid
      </MenuItem>
      <MenuItem icon="asterisk" to="/request-progress-my-suffix">
        Request Progress
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-comment-my-suffix">
        Artwork Comment
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-like-my-suffix">
        Artwork Like
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-category-my-suffix">
        Artwork Category
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-selling-my-suffix">
        Artwork Selling
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-complain-my-suffix">
        Artwork Complain
      </MenuItem>
      <MenuItem icon="asterisk" to="/selling-bid-my-suffix">
        Selling Bid
      </MenuItem>
      <MenuItem icon="asterisk" to="/request-attachment-my-suffix">
        Request Attachment
      </MenuItem>
      <MenuItem icon="asterisk" to="/request-progress-attachment-my-suffix">
        Request Progress Attachment
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
