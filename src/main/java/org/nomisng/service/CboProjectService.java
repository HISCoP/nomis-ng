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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboProjectService {
    private static final long LGA_ORG_UNIT_LEVEL_ID = 3L;
    private final CboProjectRepository cboProjectRepository;
    private final CboRepository cboRepository;
    private final DonorRepository donorRepository;
    private final ImplementerRepository implementerRepository;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final CboProjectMapper cboProjectMapper;
    private final CboProjectLocationRepository cboProjectLocationRepository;
    private final UserService userService;
    private final ApplicationUserCboProjectRepository applicationUserCboProjectRepository;
    private final UserRepository userRepository;



    public CboProjectDTO save(CboProjectDTO cboProjectDTO) {
        cboProjectRepository.findTopByCboIdAndDonorIdAndImplementerIdAndArchivedOrderByIdDesc(cboProjectDTO.getCboId(), cboProjectDTO.getDonorId(),
                cboProjectDTO.getImplementerId(), UN_ARCHIVED).ifPresent(cboProject -> {
                    throw new RecordExistException(CboProject.class, "Cbo Project", "already exist");
        });

        List<Long> organisationUnitIds = new ArrayList<>();
        Long orgUnitLevelId = LGA_ORG_UNIT_LEVEL_ID;
        List<CboProjectLocation> cboProjectLocations = new ArrayList<>();

        //Preparing the organisation Unit
        cboProjectDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            OrganisationUnit organisationUnit = organisationUnitRepository.findByIdAndArchived(organisationUnitId, UN_ARCHIVED)
                    .orElseThrow(() -> new EntityNotFoundException(OrganisationUnit.class, "id", ""+organisationUnitId));
            Long organisationUnitLevelId = organisationUnit.getOrganisationUnitLevelByOrganisationUnitLevelId().getId();

            //maybe a country
            if(organisationUnitLevelId != orgUnitLevelId ){
                throw new IllegalTypeException(OrganisationUnitLevel.class, "Level", " must be province/lga");
            } else {
                organisationUnitIds.add(organisationUnitId);
            }
        });
        //setting the organisation unit
        cboProjectDTO.setOrganisationUnitIds(organisationUnitIds);
        CboProject cboProject = cboProjectMapper.toCboProject(cboProjectDTO);
        cboProject.setArchived(UN_ARCHIVED);
        cboProject = cboProjectRepository.save(cboProject);
        final Long cboProjectId = cboProject.getId();

        //saving the cbo
        cboProjectDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            cboProjectLocations.add(new CboProjectLocation(null, cboProjectId, organisationUnitId, UN_ARCHIVED, null, null));
        });
        cboProjectLocationRepository.saveAll(cboProjectLocations);

        return cboProjectMapper.toCboProjectDTO(cboProject);
    }

    public CboProjectDTO getCboProjectById(Long id) {
        CboProject cboProject = cboProjectRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(CboProject.class, "Id", id+""));
       return cboProjectMapper.toCboProjectDTO(transformCboProjectObject(cboProject));
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
            cboProjectLocationRepository.deleteAll(cboProjectLocations);
        }

        cboProjectLocations.clear();

        CboProject cboProject = cboProjectRepository.save(cboProjectMapper.toCboProject(cboProjectDTO));
        cboProjectDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            cboProjectLocations.add(new CboProjectLocation(null, id, organisationUnitId, UN_ARCHIVED, null, null));
        });
        cboProjectLocationRepository.saveAll(cboProjectLocations);

        return transformCboProjectObject(cboProject);
    }

    public void delete(Long id) {
        CboProject cboProject = cboProjectRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(CboProject.class, "Id", id+""));

        List<CboProjectLocation> cboProjectLocation = cboProject.getCboProjectLocationsById();
        if(!cboProject.getEncountersById().isEmpty() || !cboProject.getFormData().isEmpty() ||
                !cboProject.getHouseholdMembersById().isEmpty() || !cboProject.getHouseholdMigrationsById().isEmpty() ||
                !cboProject.getHouseholds().isEmpty()){
            throw new RecordExistException(CboProject.class, "cboProject already in use",
                    "check encounter or formData or householdMember or householdMigration or household");
        }
        cboProjectLocationRepository.deleteAll(cboProjectLocation);
        cboProjectRepository.delete(cboProject);
    }

    private CboProject transformCboProjectObject(CboProject cboProject){
        String cboName = cboProject.getCboByCboId().getName();
        String donorName = cboProject.getDonorByDonorId().getName();
        String implementerName = cboProject.getImplementerByImplementerId().getName();
        List<Object> orgObject = new ArrayList<>();


        cboProject.getCboProjectLocationsById().forEach(cboProjectLocation -> {
            if(cboProjectLocation.getArchived() == UN_ARCHIVED) {
                OrganisationUnit organisationUnit = cboProjectLocation.getOrganisationUnitByOrganisationUnitId();
                OrganisationUnit parentOrgUnit = organisationUnitRepository.findByIdAndArchived(organisationUnit.getParentOrganisationUnitId(), UN_ARCHIVED).get();
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(organisationUnit.getId()));
                map.put("Name", organisationUnit.getName());
                map.put("State", parentOrgUnit.getName());
                map.put("cboProjectLocationId", String.valueOf(cboProjectLocation.getId()));
                orgObject.add(map);
            }
        });


        cboProject.setOrganisationUnits(orgObject);

        cboProject.setCboName(cboName);
        cboProject.setDonorName(donorName);
        cboProject.setImplementerName(implementerName);

        return cboProject;
    }

    public Page<CboProject> getCboProjects(Long cboId, Long donorId, Long implementerId, Pageable pageable){
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
            cboProjectList.add(transformCboProjectObject(cboProject));
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

    public List<CboProject> getAll() {
        return cboProjectRepository.findAllByArchived(UN_ARCHIVED);
    }
}