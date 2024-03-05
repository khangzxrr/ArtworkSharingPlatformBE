package com.github.khangzxrr.service;

import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.service.dto.WalletDTO;
import com.github.khangzxrr.service.dto.WalletTransactionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.Wallet}.
 */
public interface WalletService {
    Wallet getAdminWallet();

    Wallet getCurrentUserWallet();

    List<WalletTransactionDTO> getWalletTransactionsByCurrentUserWallet();

    Wallet getWalletByUserLogin(String login);
    /**
     * Save a wallet.
     *
     * @param wallet the entity to save.
     * @return the persisted entity.
     */
    Wallet save(Wallet wallet);

    /**
     * Updates a wallet.
     *
     * @param walletDTO the entity to update.
     * @return the persisted entity.
     */
    WalletDTO update(WalletDTO walletDTO);

    /**
     * Get the "id" wallet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WalletDTO> findOne(Long id);
}
