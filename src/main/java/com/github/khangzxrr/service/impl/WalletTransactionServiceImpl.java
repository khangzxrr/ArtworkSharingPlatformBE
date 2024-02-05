package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.repository.WalletTransactionRepository;
import com.github.khangzxrr.service.WalletTransactionService;
import com.github.khangzxrr.service.dto.WalletTransactionDTO;
import com.github.khangzxrr.service.mapper.WalletTransactionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.WalletTransaction}.
 */
@Service
@Transactional
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final Logger log = LoggerFactory.getLogger(WalletTransactionServiceImpl.class);

    private final WalletTransactionRepository walletTransactionRepository;

    private final WalletTransactionMapper walletTransactionMapper;

    public WalletTransactionServiceImpl(
        WalletTransactionRepository walletTransactionRepository,
        WalletTransactionMapper walletTransactionMapper
    ) {
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletTransactionMapper = walletTransactionMapper;
    }

    @Override
    public WalletTransactionDTO save(WalletTransactionDTO walletTransactionDTO) {
        log.debug("Request to save WalletTransaction : {}", walletTransactionDTO);
        WalletTransaction walletTransaction = walletTransactionMapper.toEntity(walletTransactionDTO);
        walletTransaction = walletTransactionRepository.save(walletTransaction);
        return walletTransactionMapper.toDto(walletTransaction);
    }

    @Override
    public WalletTransactionDTO update(WalletTransactionDTO walletTransactionDTO) {
        log.debug("Request to update WalletTransaction : {}", walletTransactionDTO);
        WalletTransaction walletTransaction = walletTransactionMapper.toEntity(walletTransactionDTO);
        walletTransaction = walletTransactionRepository.save(walletTransaction);
        return walletTransactionMapper.toDto(walletTransaction);
    }

    @Override
    public Optional<WalletTransactionDTO> partialUpdate(WalletTransactionDTO walletTransactionDTO) {
        log.debug("Request to partially update WalletTransaction : {}", walletTransactionDTO);

        return walletTransactionRepository
            .findById(walletTransactionDTO.getId())
            .map(existingWalletTransaction -> {
                walletTransactionMapper.partialUpdate(existingWalletTransaction, walletTransactionDTO);

                return existingWalletTransaction;
            })
            .map(walletTransactionRepository::save)
            .map(walletTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletTransactionDTO> findAll() {
        log.debug("Request to get all WalletTransactions");
        return walletTransactionRepository
            .findAll()
            .stream()
            .map(walletTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the walletTransactions where RequestProgress is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WalletTransactionDTO> findAllWhereRequestProgressIsNull() {
        log.debug("Request to get all walletTransactions where RequestProgress is null");
        return StreamSupport
            .stream(walletTransactionRepository.findAll().spliterator(), false)
            .filter(walletTransaction -> walletTransaction.getRequestProgress() == null)
            .map(walletTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the walletTransactions where SellingBid is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WalletTransactionDTO> findAllWhereSellingBidIsNull() {
        log.debug("Request to get all walletTransactions where SellingBid is null");
        return StreamSupport
            .stream(walletTransactionRepository.findAll().spliterator(), false)
            .filter(walletTransaction -> walletTransaction.getSellingBid() == null)
            .map(walletTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletTransactionDTO> findOne(Long id) {
        log.debug("Request to get WalletTransaction : {}", id);
        return walletTransactionRepository.findById(id).map(walletTransactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WalletTransaction : {}", id);
        walletTransactionRepository.deleteById(id);
    }
}
