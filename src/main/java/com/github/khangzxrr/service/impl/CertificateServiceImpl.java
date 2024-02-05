package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Certificate;
import com.github.khangzxrr.repository.CertificateRepository;
import com.github.khangzxrr.service.CertificateService;
import com.github.khangzxrr.service.dto.CertificateDTO;
import com.github.khangzxrr.service.mapper.CertificateMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.Certificate}.
 */
@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final Logger log = LoggerFactory.getLogger(CertificateServiceImpl.class);

    private final CertificateRepository certificateRepository;

    private final CertificateMapper certificateMapper;

    public CertificateServiceImpl(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public CertificateDTO save(CertificateDTO certificateDTO) {
        log.debug("Request to save Certificate : {}", certificateDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    @Override
    public CertificateDTO update(CertificateDTO certificateDTO) {
        log.debug("Request to update Certificate : {}", certificateDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    @Override
    public Optional<CertificateDTO> partialUpdate(CertificateDTO certificateDTO) {
        log.debug("Request to partially update Certificate : {}", certificateDTO);

        return certificateRepository
            .findById(certificateDTO.getId())
            .map(existingCertificate -> {
                certificateMapper.partialUpdate(existingCertificate, certificateDTO);

                return existingCertificate;
            })
            .map(certificateRepository::save)
            .map(certificateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> findAll() {
        log.debug("Request to get all Certificates");
        return certificateRepository.findAll().stream().map(certificateMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CertificateDTO> findOne(Long id) {
        log.debug("Request to get Certificate : {}", id);
        return certificateRepository.findById(id).map(certificateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Certificate : {}", id);
        certificateRepository.deleteById(id);
    }
}
