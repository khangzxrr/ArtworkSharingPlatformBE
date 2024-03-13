package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.config.ApplicationProperties;
import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.domain.SellingBid;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import com.github.khangzxrr.domain.enumeration.SellingBidStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionType;
import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.repository.ArtworkSellingRepository;
import com.github.khangzxrr.service.ArtworkSellingService;
import com.github.khangzxrr.service.NotificationService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.SellingBidDTO;
import com.github.khangzxrr.service.mapper.ArtworkSellingMapper;
import com.github.khangzxrr.service.mapper.SellingBidMapper;
import com.github.khangzxrr.web.rest.errors.ArtworkBelongToUserException;
import com.github.khangzxrr.web.rest.errors.ArtworkNotBelongToUserException;
import com.github.khangzxrr.web.rest.errors.ArtworkNotFoundException;
import com.github.khangzxrr.web.rest.errors.ArtworkSellingBidPriceMustGreaterThanCurrent;
import com.github.khangzxrr.web.rest.errors.ArtworkSellingIsFinishedException;
import com.github.khangzxrr.web.rest.errors.ArtworkSellingIsNotAuctionException;
import com.github.khangzxrr.web.rest.errors.ArtworkSellingIsNotFoundException;
import com.github.khangzxrr.web.rest.errors.ExistOnGoingArtworkSellingException;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.SellingDurationMustGreaterThanZeroException;
import com.github.khangzxrr.web.rest.errors.WalletNotEnoughMoneyForArtworkSellingBidPriceException;
import java.time.Instant;
import java.util.Arrays;
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
 * Service Implementation for managing
 * {@link com.github.khangzxrr.domain.ArtworkSelling}.
 */
@Service
@Transactional
public class ArtworkSellingServiceImpl implements ArtworkSellingService {

    private final Logger log = LoggerFactory.getLogger(ArtworkSellingServiceImpl.class);

    private final ArtworkSellingRepository artworkSellingRepository;

    private final ArtworkSellingMapper artworkSellingMapper;

    private final ArtworkRepository artworkRepository;

    private final UserService userService;

    private final WalletService walletService;

    private final ApplicationProperties applicationProperties;

    private final SellingBidMapper sellingBidMapper;

    private final NotificationService notificationService;

    public ArtworkSellingServiceImpl(
        ArtworkSellingRepository artworkSellingRepository,
        ArtworkRepository artworkRepository,
        UserService userService,
        ArtworkSellingMapper artworkSellingMapper,
        WalletService walletService,
        ApplicationProperties applicationProperties,
        SellingBidMapper sellingBidMapper,
        NotificationService notificationService
    ) {
        this.applicationProperties = applicationProperties;
        this.artworkSellingRepository = artworkSellingRepository;
        this.artworkSellingMapper = artworkSellingMapper;
        this.artworkRepository = artworkRepository;
        this.userService = userService;
        this.walletService = walletService;
        this.sellingBidMapper = sellingBidMapper;
        this.notificationService = notificationService;
    }

    @Override
    public ArtworkSellingDTO update(ArtworkSellingDTO artworkSellingDTO) {
        log.debug("Request to update ArtworkSelling : {}", artworkSellingDTO);
        ArtworkSelling artworkSelling = artworkSellingMapper.toEntity(artworkSellingDTO);
        artworkSelling = artworkSellingRepository.save(artworkSelling);
        return artworkSellingMapper.toDto(artworkSelling);
    }

    @Override
    public Optional<ArtworkSellingDTO> partialUpdate(ArtworkSellingDTO artworkSellingDTO) {
        log.debug("Request to partially update ArtworkSelling : {}", artworkSellingDTO);

        return artworkSellingRepository
            .findById(artworkSellingDTO.getId())
            .map(existingArtworkSelling -> {
                artworkSellingMapper.partialUpdate(existingArtworkSelling, artworkSellingDTO);

                return existingArtworkSelling;
            })
            .map(artworkSellingRepository::save)
            .map(artworkSellingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkSellingDTO> findAll() {
        log.debug("Request to get all ArtworkSellings");
        return artworkSellingRepository
            .findAll()
            .stream()
            .map(artworkSellingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the artworkSellings where Artwork is {@code null}.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ArtworkSellingDTO> findAllWhereArtworkIsNull() {
        log.debug("Request to get all artworkSellings where Artwork is null");
        return StreamSupport
            .stream(artworkSellingRepository.findAll().spliterator(), false)
            .filter(artworkSelling -> artworkSelling.getArtwork() == null)
            .map(artworkSellingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkSellingDTO> findOne(Long id) {
        log.debug("Request to get ArtworkSelling : {}", id);
        return artworkSellingRepository.findById(id).map(artworkSellingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkSelling : {}", id);
        artworkSellingRepository.deleteById(id);
    }

    @Override
    public ArtworkSellingDTO save(Long artworkId, ArtworkSellingDTO artworkSellingDTO) {
        log.debug("Request to save ArtworkSelling : {}", artworkSellingDTO);

        Optional<Artwork> artworkOptional = artworkRepository.findById(artworkId);

        Optional<User> userOptional = userService.getUserWithAuthorities();

        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        if (!artworkOptional.isPresent()) {
            throw new ArtworkNotFoundException();
        }

        Artwork artwork = artworkOptional.get();

        if (artwork.getOwner() != userOptional.get()) {
            throw new ArtworkNotBelongToUserException();
        }

        Optional<ArtworkSelling> onGoingArtworkSelling = getOnGoingSellingByArtworkId(artworkId);

        if (onGoingArtworkSelling.isPresent()) {
            throw new ExistOnGoingArtworkSellingException();
        }

        ArtworkSelling artworkSelling = artworkSellingMapper.toEntity(artworkSellingDTO);

        if (artworkSelling.getType() == ArtworkSellingType.DIRECT) {
            artworkSelling.setStatus(ArtworkSellingStatus.ON_SELLING);
        } else {
            if (artworkSelling.getSellingDuration() < 1) {
                throw new SellingDurationMustGreaterThanZeroException();
            }

            artworkSelling.setStatus(ArtworkSellingStatus.ON_BIDING);
        }

        artworkSelling.setArtwork(artwork);

        artworkSelling = artworkSellingRepository.save(artworkSelling);

        notificationService.subcribeUsersToTopic(String.format("/topics/artwork_selling_%d", artworkSelling.getId()), userOptional.get());

        return artworkSellingMapper.toDto(artworkSelling);
    }

    @Override
    public Optional<ArtworkSelling> getOnGoingSellingByArtworkId(Long artworkId) {
        Optional<ArtworkSelling> onGoingArtworkSelling = artworkSellingRepository.findByArtworkIdAndStatusIn(
            artworkId,
            Arrays.asList(ArtworkSellingStatus.ON_SELLING, ArtworkSellingStatus.ON_BIDING)
        );

        return onGoingArtworkSelling;
    }

    @Override
    public ArtworkSellingDTO buyDirect(Long id, Long artworkId) {
        Optional<ArtworkSelling> artworkSellingOptional = artworkSellingRepository.findByIdAndArtworkId(id, artworkId);

        if (!artworkSellingOptional.isPresent()) {
            throw new ArtworkSellingIsNotFoundException();
        }

        ArtworkSelling artworkSelling = artworkSellingOptional.get();

        Optional<User> buyerOptional = userService.getUserWithAuthorities();

        if (artworkSelling.getStatus() == ArtworkSellingStatus.FINISHED || artworkSelling.getStatus() == ArtworkSellingStatus.FAILED) {
            throw new ArtworkSellingIsFinishedException();
        }

        if (!buyerOptional.isPresent()) {
            throw new NotLoggedException();
        }

        if (artworkSelling.getArtwork().getOwner() == buyerOptional.get()) {
            throw new ArtworkBelongToUserException();
        }

        Wallet adminWallet = walletService.getAdminWallet();
        Wallet sellerWallet = walletService.getWalletByUserLogin(artworkSelling.getArtwork().getOwner().getLogin());
        Wallet buyerWallet = walletService.getWalletByUserLogin(buyerOptional.get().getLogin());

        Double serviceFee =
            (artworkSelling.getExpectedSellingPrice() * applicationProperties.getArtworkConfiguration().getServiceFeeEarnPercent()) /
            100.0d;

        Double sellingPriceLessServiceFee = artworkSelling.getExpectedSellingPrice() - serviceFee;

        WalletTransaction buyerWalletTransaction = new WalletTransaction();
        buyerWalletTransaction.setAmount(artworkSelling.getExpectedSellingPrice());
        buyerWalletTransaction.setType(WalletTransactionType.DIRECT_BUY_ARTWORK);
        buyerWalletTransaction.setStatus(WalletTransactionStatus.SUCCEED);

        buyerWallet.addTransactions(buyerWalletTransaction);

        WalletTransaction sellerWalletTransaction = new WalletTransaction();
        sellerWalletTransaction.setAmount(sellingPriceLessServiceFee);
        sellerWalletTransaction.setType(WalletTransactionType.ARTWORK_SELL_EARN);
        sellerWalletTransaction.setStatus(WalletTransactionStatus.SUCCEED);

        sellerWallet.addTransactions(sellerWalletTransaction);

        WalletTransaction adminWalletTransaction = new WalletTransaction();
        adminWalletTransaction.setAmount(serviceFee);
        adminWalletTransaction.setType(WalletTransactionType.SERVICE_FEE_EARN);
        adminWalletTransaction.setStatus(WalletTransactionStatus.SUCCEED);

        adminWallet.addTransactions(adminWalletTransaction);

        walletService.save(buyerWallet);
        walletService.save(sellerWallet);
        walletService.save(adminWallet);

        artworkSelling.setStatus(ArtworkSellingStatus.FINISHED);

        artworkSelling.getArtwork().setOwner(buyerOptional.get());

        artworkSelling = artworkSellingRepository.save(artworkSelling);

        return artworkSellingMapper.toDto(artworkSelling);
    }

    @Override
    public SellingBidDTO placeBid(Long id, Long artworkId, SellingBidDTO sellingBidDTO) {
        Optional<ArtworkSelling> artworkSellingOptional = artworkSellingRepository.findByIdAndArtworkId(id, artworkId);

        if (!artworkSellingOptional.isPresent()) {
            throw new ArtworkSellingIsNotFoundException();
        }

        ArtworkSelling artworkSelling = artworkSellingOptional.get();

        Optional<User> bidterOptional = userService.getUserWithAuthorities();

        if (artworkSelling.getStatus() == ArtworkSellingStatus.FINISHED || artworkSelling.getStatus() == ArtworkSellingStatus.FAILED) {
            throw new ArtworkSellingIsFinishedException();
        }

        if (!bidterOptional.isPresent()) {
            throw new NotLoggedException();
        }

        if (artworkSelling.getArtwork().getOwner() == bidterOptional.get()) {
            throw new ArtworkBelongToUserException();
        }

        if (artworkSelling.getType() == ArtworkSellingType.DIRECT) {
            throw new ArtworkSellingIsNotAuctionException();
        }

        // only accept higher than current price
        if (artworkSelling.getBids().stream().anyMatch(bid -> bid.getBidPrice() >= sellingBidDTO.getBidPrice())) {
            throw new ArtworkSellingBidPriceMustGreaterThanCurrent();
        }

        Wallet bidterWallet = walletService.getWalletByUserLogin(bidterOptional.get().getLogin());

        if (bidterWallet.getAmount() < sellingBidDTO.getBidPrice()) {
            throw new WalletNotEnoughMoneyForArtworkSellingBidPriceException();
        }

        SellingBid sellingBid = sellingBidMapper.toEntity(sellingBidDTO);
        sellingBid.setBidder(bidterOptional.get());
        sellingBid.setStatus(SellingBidStatus.BIDED);

        artworkSelling.addBids(sellingBid);

        artworkSelling = artworkSellingRepository.save(artworkSelling);

        notificationService.subcribeUsersToTopic(String.format("/topics/artwork_selling_%d", artworkSelling.getId()), bidterOptional.get());

        notificationService.sendToTopic(
            String.format("/topics/artwork_selling_%d", artworkSelling.getId()),
            String.format("Auction of artwork %s", artworkSelling.getArtwork().getName()),
            String.format("User %s placed bid with $%.00f", bidterOptional.get().getLogin(), sellingBid.getBidPrice())
        );

        notificationService.sendToWsTopic(String.format("/topic/artwork_selling/%d", artworkSelling.getId()), "newAuctionBid");

        return sellingBidMapper.toDto(sellingBid);
    }

    @Override
    public List<SellingBidDTO> getAllBids(Long id, Long artworkId) {
        Optional<ArtworkSelling> artworkSellingOptional = artworkSellingRepository.findByIdAndArtworkId(id, artworkId);

        if (!artworkSellingOptional.isPresent()) {
            throw new ArtworkSellingIsNotFoundException();
        }

        ArtworkSelling artworkSelling = artworkSellingOptional.get();

        return artworkSelling
            .getBids()
            .stream()
            .sorted((b1, b2) -> b2.getBidPrice().compareTo(b1.getBidPrice()))
            .map(sellingBidMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void cleanUpExpiredAuction() {
        Instant currentInstant = Instant.now();

        List<ArtworkSelling> artworkSellings = artworkSellingRepository.findAllByStatusInAndTypeIn(
            Arrays.asList(ArtworkSellingStatus.ON_BIDING),
            Arrays.asList(ArtworkSellingType.AUCTION, ArtworkSellingType.AUCTION_EXPECTED_PRICE)
        );

        artworkSellings.forEach(artworkSelling -> {
            Instant expiredDate = artworkSelling
                .getCreatedDate()
                .plus(artworkSelling.getSellingDuration(), java.time.temporal.ChronoUnit.DAYS);

            if (expiredDate.isAfter(currentInstant)) {
                return;
            }

            if (artworkSelling.getBids().isEmpty()) {
                artworkSelling.setStatus(ArtworkSellingStatus.FAILED);

                return;
            }

            SellingBid highestBid = artworkSelling.getBids().stream().max((b1, b2) -> b1.getBidPrice().compareTo(b2.getBidPrice())).get();

            Wallet sellerWallet = walletService.getWalletByUserLogin(artworkSelling.getArtwork().getOwner().getLogin());
            Wallet buyerWallet = walletService.getWalletByUserLogin(highestBid.getBidder().getLogin());
            Wallet adminWallet = walletService.getAdminWallet();

            Double serviceFee =
                (highestBid.getBidPrice() * applicationProperties.getArtworkConfiguration().getServiceFeeEarnPercent()) / 100.0d;

            Double sellingPriceLessServiceFee = highestBid.getBidPrice() - serviceFee;

            WalletTransaction buyerWalletTransaction = new WalletTransaction();
            buyerWalletTransaction.setAmount(highestBid.getBidPrice());
            buyerWalletTransaction.setType(WalletTransactionType.AUCTION_BUY_ARTWORK);

            try {
                buyerWallet.addTransactions(buyerWalletTransaction);
            } catch (Exception e) {
                artworkSelling.setStatus(ArtworkSellingStatus.FAILED);
                log.info("Auction of artwork {} is failed, because buyer wallet is not enough", artworkSelling.getArtwork().getName());

                notificationService.sendToUsers(
                    "Auction of artwork " + artworkSelling.getArtwork().getName(),
                    "Auction of artwork " + artworkSelling.getArtwork().getName() + " is failed, because your wallet is not enough",
                    highestBid.getBidder(),
                    artworkSelling.getArtwork().getOwner()
                );

                notificationService.sendToWsTopic(String.format("/topic/artwork_selling/%d", artworkSelling.getId()), "auctionFinished");

                return;
            }

            WalletTransaction sellerWalletTransaction = new WalletTransaction();
            sellerWalletTransaction.setAmount(sellingPriceLessServiceFee);
            sellerWalletTransaction.setType(WalletTransactionType.ARTWORK_SELL_EARN);

            sellerWallet.addTransactions(sellerWalletTransaction);

            WalletTransaction adminWalletTransaction = new WalletTransaction();
            adminWalletTransaction.setAmount(serviceFee);
            adminWalletTransaction.setType(WalletTransactionType.SERVICE_FEE_EARN);

            adminWallet.addTransactions(adminWalletTransaction);

            walletService.save(buyerWallet);
            walletService.save(sellerWallet);
            walletService.save(adminWallet);

            artworkSelling.setStatus(ArtworkSellingStatus.FINISHED);
            artworkSelling.getArtwork().setOwner(highestBid.getBidder());

            notificationService.sendToTopic(
                String.format("/topics/artwork_selling_%d", artworkSelling.getId()),
                String.format("Auction of artwork %s", artworkSelling.getArtwork().getName()),
                String.format(
                    "Auction of artwork %s is finished, highest bid is $%.00f of User %s",
                    artworkSelling.getArtwork().getName(),
                    highestBid.getBidPrice(),
                    highestBid.getBidder().getLogin()
                )
            );

            notificationService.sendToWsTopic(String.format("/topic/artwork_selling/%d", artworkSelling.getId()), "auctionFinished");

            log.info(
                "Auction of artwork {} is finished, highest bid is ${}",
                artworkSelling.getArtwork().getName(),
                highestBid.getBidPrice()
            );
        });

        Iterable<ArtworkSelling> artworkSellingsIterable = artworkSellings;
        artworkSellingRepository.saveAll(artworkSellingsIterable);
    }
}
