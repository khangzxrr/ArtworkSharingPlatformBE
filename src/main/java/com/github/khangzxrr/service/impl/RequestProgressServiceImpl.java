package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.repository.RequestProgressRepository;
import com.github.khangzxrr.service.RequestProgressService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
@Service
@Transactional
public class RequestProgressServiceImpl implements RequestProgressService {

    private final Logger log = LoggerFactory.getLogger(RequestProgressServiceImpl.class);

    private final RequestProgressRepository requestProgressRepository;

    private final RequestProgressMapper requestProgressMapper;

    public RequestProgressServiceImpl(RequestProgressRepository requestProgressRepository, RequestProgressMapper requestProgressMapper) {
        this.requestProgressRepository = requestProgressRepository;
        this.requestProgressMapper = requestProgressMapper;
    }

    @Override
    public RequestProgressDTO save(RequestProgressDTO requestProgressDTO) {
        log.debug("Request to save RequestProgress : {}", requestProgressDTO);
        RequestProgress requestProgress = requestProgressMapper.toEntity(requestProgressDTO);
        requestProgress = requestProgressRepository.save(requestProgress);
        return requestProgressMapper.toDto(requestProgress);
    }

    @Override
    public RequestProgressDTO update(RequestProgressDTO requestProgressDTO) {
        log.debug("Request to update RequestProgress : {}", requestProgressDTO);
        RequestProgress requestProgress = requestProgressMapper.toEntity(requestProgressDTO);
        requestProgress = requestProgressRepository.save(requestProgress);
        return requestProgressMapper.toDto(requestProgress);
    }

    @Override
    public Optional<RequestProgressDTO> partialUpdate(RequestProgressDTO requestProgressDTO) {
        log.debug("Request to partially update RequestProgress : {}", requestProgressDTO);

        return requestProgressRepository
            .findById(requestProgressDTO.getId())
            .map(existingRequestProgress -> {
                requestProgressMapper.partialUpdate(existingRequestProgress, requestProgressDTO);

                return existingRequestProgress;
            })
            .map(requestProgressRepository::save)
            .map(requestProgressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestProgressDTO> findAll() {
        log.debug("Request to get all RequestProgresses");
        return requestProgressRepository
            .findAll()
            .stream()
            .map(requestProgressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestProgressDTO> findOne(Long id) {
        log.debug("Request to get RequestProgress : {}", id);
        return requestProgressRepository.findById(id).map(requestProgressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestProgress : {}", id);
        requestProgressRepository.deleteById(id);
    }
}
