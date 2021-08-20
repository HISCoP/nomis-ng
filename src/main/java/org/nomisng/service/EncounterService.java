package org.nomisng.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.FormData;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.FormDataMapper;
import org.nomisng.repository.EncounterRepository;
import org.nomisng.repository.FormDataRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EncounterService {
    private final EncounterRepository encounterRepository;
    private final EncounterMapper encounterMapper;
    private final FormDataMapper formDataMapper;
    private final FormDataRepository formDataRepository;
    private final Long organisationUnitId = 1L;

    public List<EncounterDTO> getAllEncounters() {
        return encounterMapper.toEncounterDTO(encounterRepository.findAll());
    }

    public EncounterDTO getEncounterById(Long id) {
        Encounter encounter =  encounterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));
        return encounterMapper.toEncounterDTO(encounter);
    }

    public Encounter update(Long id, EncounterDTO encounterDTO) {
        encounterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",id+"" ));
        encounterDTO.setId(id);
        return encounterRepository.save(encounterMapper.toEncounter(encounterDTO));
    }

    public Encounter save(EncounterDTO encounterDTO) {
        FormData formData = new FormData();
        if(encounterDTO.getData() != null){
            formData.setData(encounterDTO.getData());
        }
        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setOrganisationUnitId(organisationUnitId);
        encounter = encounterRepository.save(encounter);

        formData.setEncounterId(encounter.getId());
        formData.setOrganisationalUnitId(organisationUnitId);
        formDataRepository.save(formData);
        return encounter;
    }

    public Integer delete(Long id) {
        return null;
    }


    public List<FormDataDTO> getFormDataByEncounterId(Long encounterId) {
        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id",encounterId+"" ));
        return formDataMapper.toFormDataDTOS(encounter.getFormDataById());
    }
}