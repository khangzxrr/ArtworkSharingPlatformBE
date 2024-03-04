package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.repository.WalletRepository;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.WalletDTO;
import com.github.khangzxrr.service.dto.WalletTransactionDTO;
import com.github.khangzxrr.service.mapper.WalletMapper;
import com.github.khangzxrr.service.mapper.WalletTransactionMapper;
import com.github.khangzxrr.web.rest.errors.AdminWalletNotExistException;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.UserNotExistException;
import java.util.List;
import java.util.Optional;
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

    private final WalletTransactionMapper walletTransactionMapper;

    public WalletServiceImpl(
        WalletRepository walletRepository,
        UserService userService,
        WalletMapper walletMapper,
        WalletTransactionMapper walletTransactionMapper
    ) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.walletMapper = walletMapper;
        this.walletTransactionMapper = walletTransactionMapper;
    }

    @Override
    public Wallet save(Wallet wallet) {
        log.debug("Request to save Wallet : {}", wallet);
        wallet = walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public WalletDTO update(WalletDTO walletDTO) {
        log.debug("Request to update Wallet : {}", walletDTO);
        Wallet wallet = walletMapper.toEntity(walletDTO);
        wallet = walletRepository.save(wallet);
        return walletMapper.toDto(wallet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletDTO> findOne(Long id) {
        log.debug("Request to get Wallet : {}", id);
        return walletRepository.findById(id).map(walletMapper::toDto);
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
        wallet.setAmount(0d);
        wallet.setUser(userOptional.get());

        //push to database immedietly, even when badRequest of other service..
        wallet = walletRepository.saveAndFlush(wallet);

        return wallet;
    }

    @Override
    public Wallet getAdminWallet() {
        Optional<Wallet> adminWalletOptional = walletRepository.findByAdmin();

        if (!adminWalletOptional.isPresent()) {
            throw new AdminWalletNotExistException();
        }

        return adminWalletOptional.get();
    }

    @Override
    public Wallet getWalletByUserLogin(String login) {
        Optional<User> userOptional = userService.getUserWithAuthoritiesByLogin(login);
        if (!userOptional.isPresent()) {
            throw new UserNotExistException();
        }

        User user = userOptional.get();

        log.debug("get wallet of user {} - id {}", user.getLogin(), user.getId());

        Optional<Wallet> walletOptional = walletRepository.findByUserLogin(user.getLogin());

        if (walletOptional.isPresent()) {
            return walletOptional.get();
        }

        //init new wallet if it doesnt exist

        Wallet wallet = new Wallet();
        wallet.setAmount(0d);
        wallet.setUser(user);

        //push to database immedietly, even when badRequest of other service..
        wallet = walletRepository.saveAndFlush(wallet);

        return wallet;
    }

    @Override
    public List<WalletTransactionDTO> getWalletTransactionsByCurrentUserWallet() {
        Wallet wallet = getCurrentUserWallet();
        return wallet.getTransactions().stream().map(walletTransactionMapper::toDto).toList();
    }
}
