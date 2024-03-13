package com.github.khangzxrr.web.rest.errors;

public class WalletNotEnoughMoneyForArtworkSellingBidPriceException extends BadRequestAlertException {

    public WalletNotEnoughMoneyForArtworkSellingBidPriceException() {
        super("Wallet amount is not enough for biding", "artworkSelling", "walletNotEnoughMoneyForArtworkSellingBidPriceException");
    }
}
