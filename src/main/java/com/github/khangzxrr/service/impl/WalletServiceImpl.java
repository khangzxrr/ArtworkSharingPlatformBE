package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.repository.WalletRepository;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.WalletDTO;
import com.github.khangzxrr.service.mapper.WalletMapper;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.Wallet}.
 */
@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;

    private final UserService userService;

    private final WalletMapper walletMapper;

    public WalletServiceImpl(WalletRepository walletRepository, UserService userService, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.walletMapper = walletMapper;
    }

    @Override
    public WalletDTO save(WalletDTO walletDTO) {
        log.debug("Request to save Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    @Override
    public WalletDTO update(WalletDTO walletDTO) {
        log.debug("Request to update Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    @Override
    public Optional<WalletDTO> partialUpdate(WalletDTO walletDTO) {
        log.debug("Request to partially update Wallet : {}", walletDTO);

        return walletRepository
            .findById(walletDTO.getId())
            .map(existingWallet -> {
                walletMapper.partialUpdate(existingWallet, walletDTO);

                return existingWallet;
            })
            .map(walletRepository::save)
            .map(walletMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletDTO> findAll() {
        log.debug("Request to get all Wallets");
        return walletRepository.findAll().stream().map(walletMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletDTO> findOne(Long id) {
        log.debug("Request to get Wallet : {}", id);
        return walletRepository.findById(id).map(walletMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wallet : {}", id);
        walletRepository.deleteById(id);
    }

    @Override
    public Wallet getCurrentUserWallet() {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        log.debug("get wallet of user {} - id {}", userOptional.get().getLogin(), userOptional.get().getId());

        Optional<Wallet> walletOptional = walletRepository.findByUserIsCurrentUser();

        if (walletOptional.isPresent()) {
            return walletOptional.get();
        }

        //init new wallet if it doesnt exist

        Wallet wallet = new Wallet();
        wallet.setAmount(0l);
        wallet.setUser(userOptional.get());

        //push to database immedietly, even when badRequest of other service..
        wallet = walletRepository.saveAndFlush(wallet);

        return wallet;
    }
}
