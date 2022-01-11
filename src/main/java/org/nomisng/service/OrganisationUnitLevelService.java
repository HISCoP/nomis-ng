package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.IllegalTypeException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.OrganisationUnitLevelDTO;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.domain.entity.OrganisationUnitLevel;
import org.nomisng.domain.mapper.OrganisationUnitLevelMapper;
import org.nomisng.repository.OrganisationUnitLevelRepository;
import org.nomisng.repository.OrganisationUnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final OrganisationUnitRepository organisationUnitRepository;
    private final OrganisationUnitLevelMapper organisationUnitLevelMapper;

    public OrganisationUnitLevelDTO save(OrganisationUnitLevelDTO organisationUnitLevelDTO) {
        Optional<OrganisationUnitLevel> organizationOptional = organisationUnitLevelRepository.findByNameAndArchived(organisationUnitLevelDTO.getName(), UN_ARCHIVED);
        if(organizationOptional.isPresent())throw new RecordExistException(OrganisationUnitLevel.class, "Name", organisationUnitLevelDTO.getName() +"");

        OrganisationUnitLevel organisationUnitLevel = organisationUnitLevelRepository.findByParentOrganisationUnitLevelIdAndArchived(organisationUnitLevelDTO.getParentOrganisationUnitLevelId(),
                UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(OrganisationUnitLevel.class, "ParentOrganisationUnitLevel", organisationUnitLevelDTO.getParentOrganisationUnitLevelId()+""));

        //if has no subset is 0 while has subset is 1
        if(organisationUnitLevel.getStatus() != 0){
            throw new IllegalTypeException(OrganisationUnitLevel.class, "ParentOrganisationUnitLevel", "cannot have subset");
        }

        organisationUnitLevel = organisationUnitLevelMapper.toOrganisationUnitLevel(organisationUnitLevelDTO);
        organisationUnitLevel.setArchived(UN_ARCHIVED);
        organisationUnitLevelRepository.save(organisationUnitLevel);
        return organisationUnitLevelMapper.toOrganisationUnitLevelDTO(organisationUnitLevel);
    }

    public OrganisationUnitLevelDTO update(Long id, OrganisationUnitLevelDTO organisationUnitLevelDTO) {
        organisationUnitLevelRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OrganisationUnitLevel.class, "Id", id +""));
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
        OrganisationUnitLevel organisationUnitLevel = organisationUnitLevelRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OrganisationUnitLevel.class, "Id", id +""));
        OrganisationUnitLevelDTO organisationUnitLevelDTO = organisationUnitLevelMapper.toOrganisationUnitLevelDTO(organisationUnitLevel);
        return organisationUnitLevelDTO;
    }

    public List<OrganisationUnitLevelDTO> getAllOrganizationUnitLevel(Integer status) {
        if(status != null && status < 2){
            return organisationUnitLevelMapper.toOrganisationUnitLevelDTOList(
                    organisationUnitLevelRepository.findAllByStatusAndArchivedOrderByIdAsc(status, UN_ARCHIVED));
        }
        return organisationUnitLevelMapper.toOrganisationUnitLevelDTOList(organisationUnitLevelRepository.findAllByArchivedOrderByIdAsc(UN_ARCHIVED));
    }

    public List<OrganisationUnit> getAllOrganisationUnitsByOrganizationUnitLevel(Long id) {
        organisationUnitLevelRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OrganisationUnitLevel.class, "Id", id +""));
        return organisationUnitRepository.findByOrganisationsByLevel(id, UN_ARCHIVED);
    }
}
