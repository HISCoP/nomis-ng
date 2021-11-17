package org.nomisng.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.FormData;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.FormDataMapper;
import org.nomisng.repository.EncounterRepository;
import org.nomisng.repository.FormDataRepository;
import org.nomisng.util.AccessRight;
import org.nomisng.util.JsonUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.nomisng.util.Constants.ArchiveStatus.*;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EncounterService {
    private final EncounterRepository encounterRepository;
    private final FormDataRepository formDataRepository;
    private final EncounterMapper encounterMapper;
    private final FormDataMapper formDataMapper;
    private final AccessRight accessRight;
    private final UserService userService;
    private static final String WRITE = "write";
    private static final String DELETE = "delete";
    private static final String READ = "read";

    public List<EncounterDTO> getAllEncounters() {
        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();
        List<Encounter> encounters = encounterRepository.findAllByCboProjectIdAndArchived(userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED);
        encounters.forEach(singleEncounter -> {
            //filtering by user permission
            if (!accessRight.grantAccessForm(singleEncounter.getFormCode(), permissions)) {
                return;
            }
        });
        return encounterMapper.toEncounterDTOS(encounters);
    }

    public EncounterDTO getEncounterById(Long id) {
        Encounter encounter =  encounterRepository.findByIdAndCboProjectIdAndArchived
                (id, userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));

        //Grant access
        accessRight.grantAccess(encounter.getFormCode(), Encounter.class,
                accessRight.getAllPermissionForCurrentUser());

        return encounterMapper.toEncounterDTO(encounter);
    }

    public Encounter update(Long id, EncounterDTO encounterDTO) {
        accessRight.grantAccessByAccessType(encounterDTO.getFormCode(),
                Encounter.class, WRITE, checkForEncounterAndGetPermission());

        encounterRepository.findByIdAndCboProjectIdAndArchived(id, userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));

        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setId(id);
        encounter.setArchived(UN_ARCHIVED);
        return encounterRepository.save(encounter);
    }

    public Encounter save(EncounterDTO encounterDTO) {
        Long currentCboId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        //Grant access by access type = WRITE
        accessRight.grantAccessByAccessType(encounterDTO.getFormCode(), Encounter.class, WRITE,
                accessRight.getAllPermissionForCurrentUser());

        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setCboProjectId(currentCboId);
        encounter.setArchived(UN_ARCHIVED);
        encounter = encounterRepository.save(encounter);
        final Long finalEncounterId = encounter.getId();

        encounterDTO.getFormData().forEach(formData -> {
            formData.setEncounterId(finalEncounterId);
            formData.setCboProjectId(currentCboId);
            formData.setArchived(UN_ARCHIVED);
        });
        formDataRepository.saveAll(encounterDTO.getFormData());
        return encounter;
    }

    public void delete(Long id) {
        Encounter encounter = encounterRepository.findByIdAndCboProjectIdAndArchived(id, userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));

        accessRight.grantAccessByAccessType(encounter.getFormCode(),
                Encounter.class, DELETE, checkForEncounterAndGetPermission());

        encounter.setArchived(UN_ARCHIVED);
        encounterRepository.save(encounter);
    }


    public List<FormDataDTO> getFormDataByEncounterId(Long encounterId) {
        Encounter encounter = encounterRepository.findByIdAndCboProjectIdAndArchived(encounterId, userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",encounterId+"" ));
        accessRight.grantAccessByAccessType(encounter.getFormCode(),
                Encounter.class, READ, checkForEncounterAndGetPermission());
        return formDataMapper.toFormDataDTOS(encounter.getFormData());
    }

    public Page<Encounter> getEncountersByHouseholdMemberIdAndFormCode(Long householdMemberId, String formCode, Pageable pageable) {
        accessRight.grantAccessByAccessType(formCode, Encounter.class, READ, checkForEncounterAndGetPermission());
        return encounterRepository.findAllByHouseholdMemberIdAndFormCodeAndCboProjectIdAndArchivedOrderByIdDesc(householdMemberId, formCode,
                userService.getUserWithRoles().get().getCurrentCboProjectId(),
                UN_ARCHIVED, pageable);
    }

    public Page<Encounter> getEncountersByHouseholdIdAndFormCode(Long householdId, String formCode, Pageable pageable) {
        accessRight.grantAccessByAccessType(formCode, Encounter.class, READ, checkForEncounterAndGetPermission());

        Long currentCboId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return encounterRepository.findAllByHouseholdIdAndFormCodeAndCboProjectIdAndArchivedOrderByIdDesc(householdId, formCode,
                currentCboId, UN_ARCHIVED, pageable);
    }

    public List<EncounterDTO> getEncounterDTOFromPage(Page<Encounter> encounterPage){
        List<Encounter> encounters =  encounterPage.getContent().stream()
                .map(encounter -> this.addFirstNameAndLastNameAndFormNameToEncounter(encounter))
                .collect(Collectors.toList());
        return encounterMapper.toEncounterDTOS(encounters);
    }

    public List<FormDataDTO> getFormDataDTOFromPage(Page<Encounter> encounterPage){
        List<FormData> formData =  encounterPage.getContent().stream()
                .map(Encounter::getFormData)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return formDataMapper.toFormDataDTOS(formData);
    }

    protected Encounter addFirstNameAndLastNameAndFormNameToEncounter(Encounter encounter){
        encounter.setFormName(encounter.getFormByFormCode().getName());
        if(encounter.getHouseholdMemberByHouseholdMemberId() != null) {
            //TODO: throwing error sort it out
            HouseholdMember householdMember = encounter.getHouseholdMemberByHouseholdMemberId();
            String firstName = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "firstName").replaceAll("^\"+|\"+$", "");
            String lastName = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "lastName").replaceAll("^\"+|\"+$", "");
            //String otherNames = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "otherNames");

            encounter.setFirstName(firstName);
            encounter.setLastName(lastName);
        }
        return encounter;
    }

    private Set<String> checkForEncounterAndGetPermission(){
        return accessRight.getAllPermissionForCurrentUser();
    }
}