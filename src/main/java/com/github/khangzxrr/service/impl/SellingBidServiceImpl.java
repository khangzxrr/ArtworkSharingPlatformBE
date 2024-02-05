package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.SellingBid;
import com.github.khangzxrr.repository.SellingBidRepository;
import com.github.khangzxrr.service.SellingBidService;
import com.github.khangzxrr.service.dto.SellingBidDTO;
import com.github.khangzxrr.service.mapper.SellingBidMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.SellingBid}.
 */
@Service
@Transactional
public class SellingBidServiceImpl implements SellingBidService {

    private final Logger log = LoggerFactory.getLogger(SellingBidServiceImpl.class);

    private final SellingBidRepository sellingBidRepository;

    private final SellingBidMapper sellingBidMapper;

    public SellingBidServiceImpl(SellingBidRepository sellingBidRepository, SellingBidMapper sellingBidMapper) {
        this.sellingBidRepository = sellingBidRepository;
        this.sellingBidMapper = sellingBidMapper;
    }

    @Override
    public SellingBidDTO save(SellingBidDTO sellingBidDTO) {
        log.debug("Request to save SellingBid : {}", sellingBidDTO);
        SellingBid sellingBid = sellingBidMapper.toEntity(sellingBidDTO);
        sellingBid = sellingBidRepository.save(sellingBid);
        return sellingBidMapper.toDto(sellingBid);
    }

    @Override
    public SellingBidDTO update(SellingBidDTO sellingBidDTO) {
        log.debug("Request to update SellingBid : {}", sellingBidDTO);
        SellingBid sellingBid = sellingBidMapper.toEntity(sellingBidDTO);
        sellingBid = sellingBidRepository.save(sellingBid);
        return sellingBidMapper.toDto(sellingBid);
    }

    @Override
    public Optional<SellingBidDTO> partialUpdate(SellingBidDTO sellingBidDTO) {
        log.debug("Request to partially update SellingBid : {}", sellingBidDTO);

        return sellingBidRepository
            .findById(sellingBidDTO.getId())
            .map(existingSellingBid -> {
                sellingBidMapper.partialUpdate(existingSellingBid, sellingBidDTO);

                return existingSellingBid;
            })
            .map(sellingBidRepository::save)
            .map(sellingBidMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellingBidDTO> findAll() {
        log.debug("Request to get all SellingBids");
        return sellingBidRepository.findAll().stream().map(sellingBidMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SellingBidDTO> findOne(Long id) {
        log.debug("Request to get SellingBid : {}", id);
        return sellingBidRepository.findById(id).map(sellingBidMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SellingBid : {}", id);
        sellingBidRepository.deleteById(id);
    }
}
