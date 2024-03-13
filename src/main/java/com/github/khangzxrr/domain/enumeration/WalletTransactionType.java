package com.github.khangzxrr.domain.enumeration;

/**
 * The WalletTransactionType enumeration.
 */
public enum WalletTransactionType {
    DEPOSIT,
    WITHDRAWAL,

    BUY,
    DIRECT_BUY_ARTWORK,
    AUCTION_BUY_ARTWORK,

    REFUND,
    REQUEST_EARN,
    ARTWORK_SELL_EARN,

    SERVICE_FEE_EARN,

    REQUEST_FIRST_PAYMENT_TEMP,
    WITHDRAW_REQUEST_FIRST_PAYMENT_TEMP,
    WITHDRAW_REFUND_REQUEST_FIRST_PAYMENT_TEMP,
}
