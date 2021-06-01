package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.mapper.DomainMapper;
import org.nomisng.repository.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;
    private static final int ARCHIVED = 1;
    private static final int UN_ARCHIVED = 0;

    public List getAllDomains() {
        return domainRepository.findAllByArchived(UN_ARCHIVED);
    }

    public Domain save(DomainDTO domainDTO) {
        if(domainDTO.getCode() == null || domainDTO.getCode().isEmpty()){
            domainDTO.setCode(UUID.randomUUID().toString());
        }
        Optional<Domain> domainOptional = domainRepository.findByNameAndArchived(domainDTO.getName(), UN_ARCHIVED);
        if (domainOptional.isPresent()) {
            throw new RecordExistException(Domain.class, "Name", domainDTO.getName());
        }
        Domain domain = domainMapper.toDomain(domainDTO);
        domain.setArchived(UN_ARCHIVED);

        return domainRepository.save(domain);
    }

    public DomainDTO getDomain(Long id) {
        Optional<Domain> domainOptional = domainRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!domainOptional.isPresent()) throw new EntityNotFoundException(Domain.class, "Id", id+"");

        Domain domain = domainOptional.get();
        DomainDTO domainDTO = domainMapper.toDomainDTO(domain);
        return domainDTO;
    }

    public DomainDTO getDomainByDomainCode(String domainCode) {
        Optional<Domain> domainOptional = domainRepository.findByCodeAndArchived(domainCode, UN_ARCHIVED);
        if(!domainOptional.isPresent()) {
            throw new EntityNotFoundException(Domain.class, "Domain Code", domainCode);
        }
        Domain domain = domainOptional.get();
        DomainDTO domainDTO = domainMapper.toDomainDTO(domain);
        return domainDTO;
    }

    public Domain update(Long id, DomainDTO domainDTO) {
        Optional<Domain> domainOptional = domainRepository.findByIdAndArchived(id, UN_ARCHIVED);
        log.info("domain optional  is" + domainOptional.get());
        if(!domainOptional.isPresent())throw new EntityNotFoundException(Domain.class, "Id", id +"");

        Domain domain = domainMapper.toDomain(domainDTO);
        domain.setId(id);
        return domainRepository.save(domain);
    }

    public Integer delete(Long id) {
        Optional<Domain> domainOptional = domainRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!domainOptional.isPresent())throw new EntityNotFoundException(Domain.class, "Id", id +"");

        Domain domain = domainOptional.get();
        domain.setArchived(ARCHIVED);
        domainRepository.save(domain);
        return domain.getArchived();
    }
}