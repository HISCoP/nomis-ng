package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.domain.mapper.DomainMapper;
import org.nomisng.domain.mapper.OvcServiceMapper;
import org.nomisng.repository.DomainRepository;
import org.nomisng.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;
    private final OvcServiceMapper ovcServiceMapper;


    public List getAllDomains() {
        return domainRepository.findAllByArchived(Constants.ArchiveStatus.UN_ARCHIVED);
    }

    public Domain save(DomainDTO domainDTO) {
        Optional<Domain> domainOptional = domainRepository.findByNameAndArchived(domainDTO.getName(), Constants.ArchiveStatus.UN_ARCHIVED);
        if (domainOptional.isPresent()) {
            throw new RecordExistException(Domain.class, "Name", domainDTO.getName());
        }
        Domain domain = domainMapper.toDomain(domainDTO);
        domain.setArchived(Constants.ArchiveStatus.UN_ARCHIVED);

        return domainRepository.save(domain);
    }

    public DomainDTO getDomainById(Long id) {
        Domain domain = domainRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", id +""));

        DomainDTO domainDTO = domainMapper.toDomainDTO(domain);
        return domainDTO;
    }

    public DomainDTO getDomainByDomainCode(String domainCode) {
        Domain domain = domainRepository.findByCodeAndArchived(domainCode, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Domain.class, "Domain Code", domainCode));

        DomainDTO domainDTO = domainMapper.toDomainDTO(domain);
        return domainDTO;
    }

    public Domain update(Long id, DomainDTO domainDTO) {
        Optional<Domain> domainOptional = domainRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED);
        //log.info("domain optional  is" + domainOptional.get());
        if(!domainOptional.isPresent())throw new EntityNotFoundException(Domain.class, "Id", id +"");

        Domain domain = domainMapper.toDomain(domainDTO);
        domain.setId(id);
        return domainRepository.save(domain);
    }

    public Integer delete(Long id) {
        Domain domain = domainRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", id +""));

        domain.setArchived(Constants.ArchiveStatus.ARCHIVED);
        domainRepository.save(domain);
        return domain.getArchived();
    }

    public List<OvcServiceDTO> getOvcServicesByDomainId(Long domainId){
        Domain domain = domainRepository.findByIdAndArchived(domainId, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", domainId +""));
        List<OvcService> ovcServices = domain.getServicesById().stream()
                .filter(ovcService ->ovcService.getArchived()!= null && ovcService.getArchived()== Constants.ArchiveStatus.UN_ARCHIVED)
                .sorted(Comparator.comparing(OvcService::getId).reversed())
                .collect(Collectors.toList());
        return ovcServiceMapper.toOvcServiceDTOS(ovcServices);
    }

    public List<OvcServiceDTO> getOvcServicesByDomainIdAndServiceType(Long domainId, Integer serviceType){
        Domain domain = domainRepository.findByIdAndArchived(domainId, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", domainId +""));
        List<OvcService> ovcServices = domain.getServicesById().stream()
                .filter(ovcService -> ovcService.getArchived()!= null && ovcService.getArchived()== Constants.ArchiveStatus.UN_ARCHIVED &&
                        (ovcService.getServiceType() != null && ovcService.getServiceType() == serviceType))
                .sorted(Comparator.comparing(OvcService::getId).reversed())
                .collect(Collectors.toList());
        return ovcServiceMapper.toOvcServiceDTOS(ovcServices);
    }
}