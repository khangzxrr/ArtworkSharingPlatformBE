package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import com.github.khangzxrr.domain.enumeration.WalletTransactionStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionType;
import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.repository.ArtworkSellingRepository;
import com.github.khangzxrr.repository.WalletRepository;
import com.github.khangzxrr.service.ArtworkService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.WalletTransactionService;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.mapper.ArtworkMapper;
import com.github.khangzxrr.service.mapper.WalletMapper;
import com.github.khangzxrr.service.mapper.WalletTransactionMapper;
import com.github.khangzxrr.web.rest.errors.BadRequestAlertException;
import com.github.khangzxrr.web.rest.errors.BadRequestIDAlertException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.github.khangzxrr.domain.Artwork}.
 */
@Service
@Transactional
public class ArtworkServiceImpl implements ArtworkService {

    private final Logger log = LoggerFactory.getLogger(ArtworkServiceImpl.class);

    private final ArtworkRepository artworkRepository;

    private final WalletService walletService;
    private final ArtworkMapper artworkMapper;
    private final WalletMapper walletMapper;
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final WalletTransactionMapper walletTransactionMapper;

    private final ArtworkSellingRepository artworkSellingRepository;

    public ArtworkServiceImpl(
        ArtworkRepository artworkRepository,
        ArtworkMapper artworkMapper,
        ArtworkSellingRepository artworkSellingRepository,
        WalletService walletService,
        WalletMapper walletMapper,
        WalletRepository walletRepository,
        WalletTransactionService walletTransactionService,
        WalletTransactionMapper walletTransactionMapper
    ) {
        this.artworkRepository = artworkRepository;
        this.artworkMapper = artworkMapper;
        this.artworkSellingRepository = artworkSellingRepository;
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.walletRepository = walletRepository;
        this.walletTransactionService = walletTransactionService;
        this.walletTransactionMapper = walletTransactionMapper;
    }

    @Override
    public ArtworkDTO save(ArtworkDTO artworkDTO) {
        log.debug("Request to save Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public ArtworkDTO update(ArtworkDTO artworkDTO) {
        log.debug("Request to update Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public Optional<ArtworkDTO> partialUpdate(ArtworkDTO artworkDTO) {
        log.debug("Request to partially update Artwork : {}", artworkDTO);

        return artworkRepository
            .findById(artworkDTO.getId())
            .map(existingArtwork -> {
                artworkMapper.partialUpdate(existingArtwork, artworkDTO);

                return existingArtwork;
            })
            .map(artworkRepository::save)
            .map(artworkMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkDTO> findAll() {
        log.debug("Request to get all Artworks");
        return artworkRepository.findAll().stream().map(artworkMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkDTO> findOne(Long id) {
        log.debug("Request to get Artwork : {}", id);
        return artworkRepository.findById(id).map(artworkMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artwork : {}", id);
        artworkRepository.deleteById(id);
    }

    @Override
    public ArtworkDTO DirectSellings(ArtworkDTO artworkDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DirectSellings'");
    }

    @Override
    public ArtworkDTO updateSaleDirect(ArtworkDTO artworkDTO) {
        log.debug("Request to update Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);

        Long artid = artwork.getId();

        Artwork newArtWork = artworkRepository
            .findById(artid)
            .orElseThrow(() -> new BadRequestIDAlertException("Artwork not found with ID: ", artid, "Artwork_not_found"));

        artwork.getArtworkSelling().setType(ArtworkSellingType.DIRECT);
        artwork.getArtworkSelling().setStatus(ArtworkSellingStatus.ON_GOING);

        newArtWork.setArtworkSelling(artwork.getArtworkSelling());

        artworkSellingRepository.save(newArtWork.getArtworkSelling());
        artwork = artworkRepository.save(newArtWork);
        return artworkMapper.toDto(newArtWork);
    }

    @Override
    public void cancel(Long id) {
        Optional<Artwork> artworkafterdelteSeling = artworkRepository.findById(id);

        if (artworkafterdelteSeling.isPresent()) {
            Artwork aftercancel = artworkafterdelteSeling.get();
            long idSelling = aftercancel.getArtworkSelling().getId();
            aftercancel.setArtworkSelling(null);
            artworkSellingRepository.deleteById(idSelling);
            artworkRepository.save(aftercancel);
        }
    }

    // @Override
    // public boolean purchaseArtwork(Long artworkId) {

    // Artwork artwork = artworkRepository.findById(artworkId)
    // .orElseThrow(() -> new IllegalArgumentException("Artwork not found with ID: "
    // + artworkId));

    // ArtworkSelling artworkSelling =
    // artworkSellingRepository.findById(artwork.getArtworkSelling().getId())
    // .orElseThrow(() -> new IllegalArgumentException("Artwork selling not found
    // for artwork with ID: " + artworkId));

    // Wallet creatorWallet = walletRepository.findById(artwork.getOwner().getId())
    // .orElseThrow(() -> new IllegalArgumentException("Creator wallet not found " +
    // artworkId));

    // Long artworkPrice = artworkSelling.getExpectedSellingPrice();

    // Wallet curWallet = walletService.getCurrentUserWallet();

    // if (curWallet.getAmount() < artworkPrice) {
    // return false;
    // }

    // WalletTransaction buyTransaction = new WalletTransaction();

    // buyTransaction.setAmount(artworkPrice);
    // buyTransaction.setType(WalletTransactionType.BUY);
    // buyTransaction.status(WalletTransactionStatus.SUCCEED);
    // buyTransaction.setCreateAt(LocalDate.now());
    // curWallet.addTransactions(buyTransaction);

    // walletTransactionService.save(walletTransactionMapper.toDto(buyTransaction));
    // walletService.save(walletMapper.toDto(curWallet));

    // WalletTransaction earnTransaction = new WalletTransaction();

    // earnTransaction.setAmount(artworkPrice);
    // earnTransaction.setType(WalletTransactionType.DIRECT_SELL_EARN);
    // earnTransaction.status(WalletTransactionStatus.SUCCEED);
    // earnTransaction.setCreateAt(LocalDate.now());
    // creatorWallet.addTransactions(earnTransaction);

    // walletTransactionService.save(walletTransactionMapper.toDto(earnTransaction));
    // walletService.save(walletMapper.toDto(creatorWallet));

    // artwork.setOwner(curWallet.getUser());
    // cancel(artworkId);

    // return true;

    // }

    @Override
    public int purchaseArtwork(Long artworkId) {
        Artwork artwork = artworkRepository
            .findById(artworkId)
            .orElseThrow(() -> new BadRequestIDAlertException("Artwork not found with ID: ", artworkId, "Artwork_not_found"));

        ArtworkSelling artworkSelling = artworkSellingRepository
            .findById(artwork.getArtworkSelling().getId())
            .orElseThrow(() ->
                new BadRequestIDAlertException("Artwork selling not found for artwork with ID: ", artworkId, "ArtworkSelling_not_found")
            );

        Wallet creatorWallet = walletRepository
            .findByUserId(artwork.getOwner().getId())
            .orElseThrow(() -> new BadRequestIDAlertException("Creator wallet not found ", artworkId, "Creator"));

        Long artworkPrice = artworkSelling.getExpectedSellingPrice();

        Wallet curWallet = walletService.getCurrentUserWallet();

        if (curWallet.getAmount() < artworkPrice) {
            return 2;
        }

        if (artwork.getOwner().getId() == curWallet.getUser().getId()) {
            return 3;
        }

        if (!artwork.getArtworkSelling().getStatus().equals(ArtworkSellingStatus.ON_GOING)) {
            return 4;
        }

        Wallet adminwallet = walletService.getAdminWallet();

        // Tạo giao dịch mua Artwork và lưu vào ví của người dùng hiện tại
        WalletTransaction buyTransaction = createBuyTransaction(artworkPrice);
        curWallet.addTransactions(buyTransaction);
        walletTransactionService.save(walletTransactionMapper.toDto(buyTransaction));
        walletService.save(curWallet);

        // Tạo giao dịch kiếm tiền cho người tạo Artwork và lưu vào ví của họ
        WalletTransaction earnTransaction = createEarnTransaction(artworkPrice);
        creatorWallet.addTransactions(earnTransaction);
        walletTransactionService.save(walletTransactionMapper.toDto(earnTransaction));
        walletService.save(creatorWallet);

        //Cộng phí vào tài khoàn admin
        WalletTransaction feeTransaction = createEarnTransaction(artworkPrice);
        adminwallet.addTransactions(feeTransaction);
        walletTransactionService.save(walletTransactionMapper.toDto(feeTransaction));
        walletService.save(adminwallet);

        // Cập nhật người sở hữu mới cho Artwork và hủy bán Artwork
        artwork.setOwner(curWallet.getUser());
        cancel(artworkId);

        return 1;
    }

    private WalletTransaction createBuyTransaction(double amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setAmount(amount);
        transaction.setType(WalletTransactionType.BUY);
        transaction.setStatus(WalletTransactionStatus.SUCCEED);
        transaction.setCreateAt(LocalDate.now());
        return transaction;
    }

    private WalletTransaction createEarnTransaction(double amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setAmount(amount);
        transaction.setType(WalletTransactionType.DIRECT_SELL_EARN);
        transaction.setStatus(WalletTransactionStatus.SUCCEED);
        transaction.setCreateAt(LocalDate.now());
        return transaction;
    }

    private WalletTransaction createFeeTransaction(double amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setAmount(amount);
        transaction.setType(WalletTransactionType.DIRECT_SELLING_FEE_EARN);
        transaction.setStatus(WalletTransactionStatus.SUCCEED);
        transaction.setCreateAt(LocalDate.now());
        return transaction;
    }
}
