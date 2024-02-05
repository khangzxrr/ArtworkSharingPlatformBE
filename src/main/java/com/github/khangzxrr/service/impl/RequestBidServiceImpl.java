package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.repository.RequestBidRepository;
import com.github.khangzxrr.service.RequestBidService;
import com.github.khangzxrr.service.dto.RequestBidDTO;
import com.github.khangzxrr.service.mapper.RequestBidMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.RequestBid}.
 */
@Service
@Transactional
public class RequestBidServiceImpl implements RequestBidService {

    private final Logger log = LoggerFactory.getLogger(RequestBidServiceImpl.class);

    private final RequestBidRepository requestBidRepository;

    private final RequestBidMapper requestBidMapper;

    public RequestBidServiceImpl(RequestBidRepository requestBidRepository, RequestBidMapper requestBidMapper) {
        this.requestBidRepository = requestBidRepository;
        this.requestBidMapper = requestBidMapper;
    }

    @Override
    public RequestBidDTO save(RequestBidDTO requestBidDTO) {
        log.debug("Request to save RequestBid : {}", requestBidDTO);
        RequestBid requestBid = requestBidMapper.toEntity(requestBidDTO);
        requestBid = requestBidRepository.save(requestBid);
        return requestBidMapper.toDto(requestBid);
    }

    @Override
    public RequestBidDTO update(RequestBidDTO requestBidDTO) {
        log.debug("Request to update RequestBid : {}", requestBidDTO);
        RequestBid requestBid = requestBidMapper.toEntity(requestBidDTO);
        requestBid = requestBidRepository.save(requestBid);
        return requestBidMapper.toDto(requestBid);
    }

    @Override
    public Optional<RequestBidDTO> partialUpdate(RequestBidDTO requestBidDTO) {
        log.debug("Request to partially update RequestBid : {}", requestBidDTO);

        return requestBidRepository
            .findById(requestBidDTO.getId())
            .map(existingRequestBid -> {
                requestBidMapper.partialUpdate(existingRequestBid, requestBidDTO);

                return existingRequestBid;
            })
            .map(requestBidRepository::save)
            .map(requestBidMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestBidDTO> findAll() {
        log.debug("Request to get all RequestBids");
        return requestBidRepository.findAll().stream().map(requestBidMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestBidDTO> findOne(Long id) {
        log.debug("Request to get RequestBid : {}", id);
        return requestBidRepository.findById(id).map(requestBidMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestBid : {}", id);
        requestBidRepository.deleteById(id);
    }
}
