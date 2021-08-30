package org.nomisng.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.FormData;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.FormDataMapper;
import org.nomisng.repository.EncounterRepository;
import org.nomisng.repository.FormDataRepository;
import org.nomisng.util.Constants;
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
    private final Long organisationUnitId = 1L;

    public List<EncounterDTO> getAllEncounters() {
        return encounterMapper.toEncounterDTOS(encounterRepository.findAllByArchived(UN_ARCHIVED));
    }

    public EncounterDTO getEncounterById(Long id) {
        Encounter encounter =  encounterRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));
        return encounterMapper.toEncounterDTO(encounter);
    }

    public Encounter update(Long id, EncounterDTO encounterDTO) {
        encounterRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));
        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setId(id);
        encounter.setArchived(UN_ARCHIVED);
        return encounterRepository.save(encounter);
    }

    public Encounter save(EncounterDTO encounterDTO) {
        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setOrganisationUnitId(organisationUnitId);
        encounter = encounterRepository.save(encounter);
        final Long finalEncounterId = encounter.getId();

        encounterDTO.getFormData().forEach(formData -> {
            formData.setEncounterId(finalEncounterId);
            formData.setOrganisationUnitId(organisationUnitId);
        });
        formDataRepository.saveAll(encounterDTO.getFormData());
        return encounter;
    }

    public void delete(Long id) {
        Encounter encounter = encounterRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));
        encounter.setArchived(UN_ARCHIVED);
        encounterRepository.save(encounter);
    }


    public List<FormDataDTO> getFormDataByEncounterId(Long encounterId) {
        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",encounterId+"" ));
        return formDataMapper.toFormDataDTOS(encounter.getFormData());
    }

    public Page<Encounter> getEncountersByHouseholdMemberIdAndFormCode(Long householdMemberId, String formCode, Pageable pageable) {
        return encounterRepository.findAllByHouseholdMemberIdAndFormCodeAndArchivedOrderByIdDesc(householdMemberId, formCode, UN_ARCHIVED, pageable);
    }

    public Page<Encounter> getEncountersByHouseholdIdAndFormCode(Long householdId, String formCode, Pageable pageable) {
        return encounterRepository.findAllByHouseholdIdAndFormCodeAndArchivedOrderByIdDesc(householdId, formCode, UN_ARCHIVED, pageable);
    }

    //TODO: Test...
    public List<EncounterDTO> getEncounterDTOFromPage(Page<Encounter> encounterPage){
       List<Encounter> encounters =  encounterPage.getContent().stream()
                .map(encounter -> this.addFirstNameAndLastNameAndFormNameToEncounter(encounter))
                .collect(Collectors.toList());
        return encounterMapper.toEncounterDTOS(encounters);
    }

    protected Encounter addFirstNameAndLastNameAndFormNameToEncounter(Encounter encounter){
        encounter.setFormName(encounter.getFormByFormCode().getName());
        if(encounter.getHouseholdMemberByHouseholdMemberId() != null) {
            HouseholdMember householdMember = encounter.getHouseholdMemberByHouseholdMemberId();
            String firstName = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "firstName").replaceAll("^\"+|\"+$", "");
            String lastName = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "lastName").replaceAll("^\"+|\"+$", "");
            //String otherNames = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "otherNames");

            encounter.setFirstName(firstName);
            encounter.setLastName(lastName);
        }
        return encounter;
    }
}