package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.OrganisationUnitLevelDTO;
import org.nomisng.domain.entity.OrganisationUnitLevel;
import org.nomisng.domain.mapper.OrganisationUnitLevelMapper;
import org.nomisng.repository.OrganisationUnitLevelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrganisationUnitLevelService {
    private final OrganisationUnitLevelRepository organisationUnitLevelRepository;
    private final OrganisationUnitLevelMapper organisationUnitLevelMapper;

    public OrganisationUnitLevelDTO save(OrganisationUnitLevelDTO organisationUnitLevelDTO) {
        Optional<OrganisationUnitLevel> organizationOptional = organisationUnitLevelRepository.findByNameAndArchived(organisationUnitLevelDTO.getName(), UN_ARCHIVED);
        if(organizationOptional.isPresent())throw new RecordExistException(OrganisationUnitLevel.class, "Name", organisationUnitLevelDTO.getName() +"");
        OrganisationUnitLevel organisationUnitLevel = organisationUnitLevelMapper.toOrganisationUnitLevel(organisationUnitLevelDTO);
        organisationUnitLevel.setArchived(UN_ARCHIVED);
        organisationUnitLevelRepository.save(organisationUnitLevel);
        return organisationUnitLevelMapper.toOrganisationUnitLevelDTO(organisationUnitLevel);
    }

    public OrganisationUnitLevelDTO update(Long id, OrganisationUnitLevelDTO organisationUnitLevelDTO) {
        Optional<OrganisationUnitLevel> organizationOptional = organisationUnitLevelRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!organizationOptional.isPresent())throw new EntityNotFoundException(OrganisationUnitLevel.class, "Id", id +"");
        OrganisationUnitLevel organisationUnitLevel = organisationUnitLevelMapper.toOrganisationUnitLevel(organisationUnitLevelDTO);
        organisationUnitLevel.setId(id);
        organisationUnitLevel.setArchived(UN_ARCHIVED);
        organisationUnitLevelRepository.save(organisationUnitLevel);
        return organisationUnitLevelMapper.toOrganisationUnitLevelDTO(organisationUnitLevel);
    }

    /*public Integer delete(Long id) {
        Optional<OrganisationUnitLevel> organizationOptional = organisationUnitLevelRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if (!organizationOptional.isPresent())throw new EntityNotFoundException(OrganisationUnitLevel.class, "Id", id +"");
        return organizationOptional.get().getArchived();
    }*/

    public OrganisationUnitLevelDTO getOrganizationUnitLevel(Long id){
        Optional<OrganisationUnitLevel> organizationOptional = organisationUnitLevelRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if (!organizationOptional.isPresent())throw new EntityNotFoundException(OrganisationUnitLevel.class, "Id", id +"");
        OrganisationUnitLevelDTO organisationUnitLevelDTO = organisationUnitLevelMapper.toOrganisationUnitLevelDTO(organizationOptional.get());
        return organisationUnitLevelDTO;
    }

    public List<OrganisationUnitLevelDTO> getAllOrganizationUnitLevel() {
        return organisationUnitLevelMapper.toOrganisationUnitLevelDTOList(organisationUnitLevelRepository.findAllByArchivedOrderByIdAsc(UN_ARCHIVED));
    }
}
