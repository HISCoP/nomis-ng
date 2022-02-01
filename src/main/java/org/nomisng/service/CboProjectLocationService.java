package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.IllegalTypeException;
import org.nomisng.domain.dto.CboProjectLocationDTO;
import org.nomisng.domain.entity.CboProjectLocation;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.domain.entity.OrganisationUnitLevel;
import org.nomisng.domain.mapper.CboProjectLocationMapper;
import org.nomisng.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboProjectLocationService {
    private static final int LGA_ORG_UNIT_LEVEL = 3;
    private final CboProjectLocationRepository cboProjectLocationRepository;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final UserService userService;
    private final CboProjectLocationMapper cboProjectLocationMapper;


    public List<OrganisationUnit> getOrganisationUnitByCboProjectId() {
        Long cboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return organisationUnitRepository.findAllByCboProjectId(cboProjectId);
    }

    public List<OrganisationUnit> getState() {
        Long cboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return organisationUnitRepository.findStateByCboProjectId(cboProjectId);
    }

    public List<OrganisationUnit> getLga(Long stateId) {
        Long cboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return organisationUnitRepository.findLgaByStateIdAndCboProjectId(stateId, cboProjectId);
    }

    public List<CboProjectLocation> update(List<CboProjectLocationDTO> cboProjectLocationDTOS) {
        List<CboProjectLocation> newCboProjectLocations = new ArrayList<>();
        Long cboProjectId = null;

        for (CboProjectLocationDTO cboProjectLocationDTO : cboProjectLocationDTOS) {
            if (cboProjectLocationDTO.getOrganisationUnitId() == null || cboProjectLocationDTO.getCboProjectId() == null) {
                throw new IllegalTypeException(CboProjectLocation.class, "organisationUnitId or cboProjectId",
                        " not available " + cboProjectLocationDTO);
            }
            organisationUnitRepository.findByIdAndArchived(cboProjectLocationDTO.getOrganisationUnitId(),
                    UN_ARCHIVED).ifPresent(organisationUnit -> {
                if(organisationUnit.getOrganisationUnitLevelId() != LGA_ORG_UNIT_LEVEL){
                    throw new IllegalTypeException(OrganisationUnitLevel.class, "organisationUnitLevel", " not LGA");
                }
            });
            cboProjectId = cboProjectLocationDTO.getCboProjectId();
            newCboProjectLocations.add(cboProjectLocationMapper.toCboProjectLocation(cboProjectLocationDTO));
        }
        List<CboProjectLocation> formerCboProjectLocations = cboProjectLocationRepository.findAllByCboProjectIdOrderByIdDesc(cboProjectId);
        cboProjectLocationRepository.deleteAll(formerCboProjectLocations);
        return cboProjectLocationRepository.saveAll(newCboProjectLocations);
    }
}