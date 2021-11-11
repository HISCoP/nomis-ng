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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    /*public List<CboProjectDTO> getAllCboProjects() {
        List<CboProject> cboProjectList = new ArrayList<>();
        cboProjectRepository.findAllByArchived(UN_ARCHIVED).forEach(cboProject -> {
            cboProjectList.add(setNames(cboProject));
        });
        return cboProjectMapper.toCboProjectDTOS(cboProjectRepository.findAll());
    }*/



    public CboProjectDTO save(CboProjectDTO cboProjectDTO) {
        cboProjectRepository.findByCboIdAndDonorIdAndImplementerIdAndArchived(cboProjectDTO.getCboId(), cboProjectDTO.getDonorId(),
                cboProjectDTO.getImplementerId(), constant.UN_ARCHIVED).ifPresent(cboProject -> {
                    throw new RecordExistException(CboProject.class, "Cbo Project", "already exist");
        });

        List<Long> organisationUnitIds = new ArrayList<>();
        Long orgUnitLevelId = 4L;
        List<CboProjectLocation> cboProjectLocations = new ArrayList<>();

        //Preparing the organisation Unit
        cboProjectDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            OrganisationUnit organisationUnit = organisationUnitRepository.findByIdAndArchived(organisationUnitId, constant.UN_ARCHIVED)
                    .orElseThrow(() -> new EntityNotFoundException(OrganisationUnit.class, "id", ""+organisationUnitId));
            Long organisationUnitLevelId = organisationUnit.getOrganisationUnitLevelByOrganisationUnitLevelId().getId();

            //maybe a country
            if(organisationUnitLevelId < 2 ){
                throw new IllegalTypeException(OrganisationUnitLevel.class, "Level", ""+organisationUnitLevelId + " must be province/lga or ward");
            }

            //not a ward level
            if(organisationUnitLevelId != orgUnitLevelId) {
                Pageable pageable = PageRequest.of(0, 1000);

                Page<OrganisationUnitHierarchy> page = organisationUnitService.getOrganisationUnitHierarchies(organisationUnit.getId(), orgUnitLevelId,pageable);
                List<Long> orgUnitIds = page.getContent().stream().map(OrganisationUnitHierarchy::getOrganisationUnitId)
                        .collect(Collectors.toList());
                organisationUnitIds.addAll(orgUnitIds);
            }
            //Is a ward
            if(organisationUnitLevelId == orgUnitLevelId) {
                organisationUnitIds.add(organisationUnitId) ;
            }
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

    public CboProject update(Long id, CboProjectDTO cboProjectDTO) {
        cboProjectRepository.findById(id)
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

        cboProject.setOrganisationUnits(cboProject.getCboProjectLocationsById().stream()
                .map(cboProjectLocation -> {map.put(cboProjectLocation.getOrganisationUnitByOrganisationUnitId().getId(),
                        cboProjectLocation.getOrganisationUnitByOrganisationUnitId().getName());
                    return map;})
                .collect(Collectors.toList()));

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
}