package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.WalletTransactionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.WalletTransaction}.
 */
public interface WalletTransactionService {
    /**
     * Save a walletTransaction.
     *
     * @param walletTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    WalletTransactionDTO save(WalletTransactionDTO walletTransactionDTO);

    /**
     * Updates a walletTransaction.
     *
     * @param walletTransactionDTO the entity to update.
     * @return the persisted entity.
     */
    WalletTransactionDTO update(WalletTransactionDTO walletTransactionDTO);

    /**
     * Partially updates a walletTransaction.
     *
     * @param walletTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WalletTransactionDTO> partialUpdate(WalletTransactionDTO walletTransactionDTO);

    /**
     * Get all the walletTransactions.
     *
     * @return the list of entities.
     */
    List<WalletTransactionDTO> findAll();

    /**
     * Get all the WalletTransactionDTO where RequestProgress is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<WalletTransactionDTO> findAllWhereRequestProgressIsNull();
    /**
     * Get all the WalletTransactionDTO where SellingBid is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<WalletTransactionDTO> findAllWhereSellingBidIsNull();

    /**
     * Get the "id" walletTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WalletTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" walletTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
