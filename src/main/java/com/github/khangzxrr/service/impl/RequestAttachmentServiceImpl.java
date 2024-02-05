package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.RequestAttachment;
import com.github.khangzxrr.repository.RequestAttachmentRepository;
import com.github.khangzxrr.service.RequestAttachmentService;
import com.github.khangzxrr.service.dto.RequestAttachmentDTO;
import com.github.khangzxrr.service.mapper.RequestAttachmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.RequestAttachment}.
 */
@Service
@Transactional
public class RequestAttachmentServiceImpl implements RequestAttachmentService {

    private final Logger log = LoggerFactory.getLogger(RequestAttachmentServiceImpl.class);

    private final RequestAttachmentRepository requestAttachmentRepository;

    private final RequestAttachmentMapper requestAttachmentMapper;

    public RequestAttachmentServiceImpl(
        RequestAttachmentRepository requestAttachmentRepository,
        RequestAttachmentMapper requestAttachmentMapper
    ) {
        this.requestAttachmentRepository = requestAttachmentRepository;
        this.requestAttachmentMapper = requestAttachmentMapper;
    }

    @Override
    public RequestAttachmentDTO save(RequestAttachmentDTO requestAttachmentDTO) {
        log.debug("Request to save RequestAttachment : {}", requestAttachmentDTO);
        RequestAttachment requestAttachment = requestAttachmentMapper.toEntity(requestAttachmentDTO);
        requestAttachment = requestAttachmentRepository.save(requestAttachment);
        return requestAttachmentMapper.toDto(requestAttachment);
    }

    @Override
    public RequestAttachmentDTO update(RequestAttachmentDTO requestAttachmentDTO) {
        log.debug("Request to update RequestAttachment : {}", requestAttachmentDTO);
        RequestAttachment requestAttachment = requestAttachmentMapper.toEntity(requestAttachmentDTO);
        requestAttachment = requestAttachmentRepository.save(requestAttachment);
        return requestAttachmentMapper.toDto(requestAttachment);
    }

    @Override
    public Optional<RequestAttachmentDTO> partialUpdate(RequestAttachmentDTO requestAttachmentDTO) {
        log.debug("Request to partially update RequestAttachment : {}", requestAttachmentDTO);

        return requestAttachmentRepository
            .findById(requestAttachmentDTO.getId())
            .map(existingRequestAttachment -> {
                requestAttachmentMapper.partialUpdate(existingRequestAttachment, requestAttachmentDTO);

                return existingRequestAttachment;
            })
            .map(requestAttachmentRepository::save)
            .map(requestAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestAttachmentDTO> findAll() {
        log.debug("Request to get all RequestAttachments");
        return requestAttachmentRepository
            .findAll()
            .stream()
            .map(requestAttachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestAttachmentDTO> findOne(Long id) {
        log.debug("Request to get RequestAttachment : {}", id);
        return requestAttachmentRepository.findById(id).map(requestAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestAttachment : {}", id);
        requestAttachmentRepository.deleteById(id);
    }
}
