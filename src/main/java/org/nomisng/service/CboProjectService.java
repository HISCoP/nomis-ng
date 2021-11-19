package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.IllegalTypeException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.CboProjectDTO;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.CboProjectMapper;
import org.nomisng.repository.*;
import org.nomisng.util.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboProjectService {
    private final CboProjectRepository cboProjectRepository;
    private final CboRepository cboRepository;
    private final DonorRepository donorRepository;
    private final ImplementerRepository implementerRepository;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final CboProjectMapper cboProjectMapper;
    private final Constants.ArchiveStatus constant;
    private final OrganisationUnitService organisationUnitService;
    private final CboProjectLocationRepository cboProjectLocationRepository;
    private final UserService userService;
    private final static int UN_ARCHIVED = 0;
    private final ApplicationUserCboProjectRepository applicationUserCboProjectRepository;
    private final UserRepository userRepository;



    public CboProjectDTO save(CboProjectDTO cboProjectDTO) {
        cboProjectRepository.findTopByCboIdAndDonorIdAndImplementerIdAndArchivedOrderByIdDesc(cboProjectDTO.getCboId(), cboProjectDTO.getDonorId(),
                cboProjectDTO.getImplementerId(), constant.UN_ARCHIVED).ifPresent(cboProject -> {
                    throw new RecordExistException(CboProject.class, "Cbo Project", "already exist");
        });

        List<Long> organisationUnitIds = new ArrayList<>();
        Long orgUnitLevelId = 3L;
        List<CboProjectLocation> cboProjectLocations = new ArrayList<>();

        //Preparing the organisation Unit
        cboProjectDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            OrganisationUnit organisationUnit = organisationUnitRepository.findByIdAndArchived(organisationUnitId, constant.UN_ARCHIVED)
                    .orElseThrow(() -> new EntityNotFoundException(OrganisationUnit.class, "id", ""+organisationUnitId));
            Long organisationUnitLevelId = organisationUnit.getOrganisationUnitLevelByOrganisationUnitLevelId().getId();

            //maybe a country
            if(organisationUnitLevelId != orgUnitLevelId ){
                throw new IllegalTypeException(OrganisationUnitLevel.class, "Level", " must be province/lga");
            } else {
                /*Pageable pageable = PageRequest.of(0, 1000);

                Page<OrganisationUnitHierarchy> page = organisationUnitService.getOrganisationUnitHierarchies(organisationUnit.getId(), orgUnitLevelId,pageable);
                List<Long> orgUnitIds = page.getContent().stream().map(OrganisationUnitHierarchy::getOrganisationUnitId)
                        .collect(Collectors.toList());*/
                organisationUnitIds.add(organisationUnitId);
            }
            /*//Is a ward
            if(organisationUnitLevelId == orgUnitLevelId) {
                organisationUnitIds.add(organisationUnitId) ;
            }*/
        });
        //setting the organisation unit
        cboProjectDTO.setOrganisationUnitIds(organisationUnitIds);
        CboProject cboProject = cboProjectMapper.toCboProject(cboProjectDTO);
        cboProject.setArchived(constant.UN_ARCHIVED);
        cboProject = cboProjectRepository.save(cboProject);
        final Long cboProjectId = cboProject.getId();

        //saving the cbo
        cboProjectDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            cboProjectLocations.add(new CboProjectLocation(null, cboProjectId, organisationUnitId, null, null));
        });
        cboProjectLocationRepository.saveAll(cboProjectLocations);

        return cboProjectMapper.toCboProjectDTO(cboProject);
    }

    public CboProjectDTO getCboProjectById(Long id) {
        CboProject cboProject = cboProjectRepository.findByIdAndArchived(id, constant.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(CboProject.class, "Id", id+""));
       return cboProjectMapper.toCboProjectDTO(setNames(cboProject));
    }

    /*public List<OrganisationUnit> getOrganisationUnitByCboProjectId() {
        Long cboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        CboProject cboProject = cboProjectRepository.findByIdAndArchived(cboProjectId, constant.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(CboProject.class, "cboProjectId", cboProjectId+""));
        return cboProject.getOrganisationUnits();
    }*/

    public CboProject update(Long id, CboProjectDTO cboProjectDTO) {
        cboProjectRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(CboProject.class, "Id", id+""));

        List<CboProjectLocation> cboProjectLocations = cboProjectLocationRepository
                .findAllByIdAndOrganisationUnitIn(id, cboProjectDTO.getOrganisationUnitIds());

        if(!cboProjectLocations.isEmpty()){
            List<Long> orgUnitIds = cboProjectLocations.stream()
                    .map(CboProjectLocation::getOrganisationUnitId)
                    .collect(Collectors.toList());
            throw new RecordExistException(CboProjectLocation.class, "Cbo Project Location", orgUnitIds.toString());
        }

        CboProject cboProject = cboProjectRepository.save(cboProjectMapper.toCboProject(cboProjectDTO));
        cboProjectDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            cboProjectLocations.add(new CboProjectLocation(null, id, organisationUnitId, null, null));
        });
        cboProjectLocationRepository.saveAll(cboProjectLocations);

        return setNames(cboProject);
    }

    public Integer delete(Long id) {
        return null;
    }

    private CboProject setNames(CboProject cboProject){
        String cboName = cboProject.getCboByCboId().getName();
        String donorName = cboProject.getDonorByDonorId().getName();
        String implementerName = cboProject.getImplementerByImplementerId().getName();
        HashMap<Long, String> map = new HashMap<>();

        cboProject.getCboProjectLocationsById().forEach(cboProjectLocation -> {
            map.put(cboProjectLocation.getOrganisationUnitByOrganisationUnitId().getId(),
                    cboProjectLocation.getOrganisationUnitByOrganisationUnitId().getName());
        });

        cboProject.setOrganisationUnits(Collections.singletonList(map));

        cboProject.setCboName(cboName);
        cboProject.setDonorName(donorName);
        cboProject.setImplementerName(implementerName);

        return cboProject;
    }

    public Page<CboProject> getCboProject(Long cboId, Long donorId, Long implementerId, Pageable pageable){
        List<Long> cboIds = getIds(cboId);
        List<Long> donorIds = getIds(donorId);
        List<Long> implementerIds = getIds(implementerId);

        if(cboIds.isEmpty()){
            cboIds = cboRepository.findAllId();
        }

        if(donorIds.isEmpty()){
            donorIds = donorRepository.findAllId();
        }

        if(implementerIds.isEmpty()){
            implementerIds = implementerRepository.findAllId();
        }

      return cboProjectRepository.findAllCboProjects(cboIds, donorIds, implementerIds, pageable);
    }

    public List<CboProjectDTO> getCboProjectsFromPage(Page<CboProject> page){
        List<CboProject> cboProjectList = new ArrayList<>();

        page.getContent().forEach(cboProject -> {
            cboProjectList.add(setNames(cboProject));
        });
        return cboProjectMapper.toCboProjectDTOS(cboProjectList);
    }

    private List<Long> getIds(Long id) {
        List<Long> ids = new ArrayList<>();
        if (id != null && id != 0) {
            ids.add(id);
        }
        return ids;
    }

    public void switchCboProject(Long id) {
        if(cboProjectLocationRepository.findAllById(id).isEmpty()){
            new EntityNotFoundException(CboProject.class, "Id", id+"");
        }

        User user = userService.getUserWithRoles().get();
        Long userId = user.getId();

        applicationUserCboProjectRepository.findByApplicationUserIdAndCboProjectId(userId, id)
                .orElseThrow(() -> new EntityNotFoundException(CboProject.class, "User & Cbo Project", "does not match"));

        user.setCurrentCboProjectId(id);
        userRepository.save(user);
    }
}