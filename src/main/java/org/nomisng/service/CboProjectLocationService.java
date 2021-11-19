package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.IllegalTypeException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.CboProjectDTO;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.CboProjectLocation;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.domain.entity.OrganisationUnitLevel;
import org.nomisng.domain.mapper.CboProjectMapper;
import org.nomisng.repository.*;
import org.nomisng.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboProjectLocationService {
    private final CboProjectLocationRepository cboProjectLocationRepository;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final UserService userService;


    public List<OrganisationUnit> getOrganisationUnitByCboProjectId() {
        Long cboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return organisationUnitRepository.findAllByCboProjectIdId(cboProjectId);
    }

    public List<OrganisationUnit> getState() {
        Long cboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return organisationUnitRepository.findStateByCboProjectId(cboProjectId);
    }

    public List<OrganisationUnit> getLga(Long stateId) {
        Long cboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return organisationUnitRepository.findLgaByStateIdAndCboProjectId(stateId, cboProjectId);
    }
}