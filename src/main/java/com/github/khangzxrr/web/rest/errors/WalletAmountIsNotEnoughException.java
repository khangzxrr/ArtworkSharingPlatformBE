package com.github.khangzxrr.web.rest.errors;

public class WalletAmountIsNotEnoughException extends BadRequestAlertException {

    public WalletAmountIsNotEnoughException() {
        super(ErrorConstants.WALLET_AMOUNT_IS_NOT_ENOUGH, "Wallet amount is not enough", "wallet", "walletAmountIsNotEnough");
    }
}
