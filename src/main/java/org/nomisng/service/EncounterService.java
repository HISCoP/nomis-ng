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
import org.nomisng.util.Constants;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static org.nomisng.util.Constants.ArchiveStatus.*;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EncounterService {
    private final EncounterRepository encounterRepository;
    private final EncounterMapper encounterMapper;
    private final FormDataMapper formDataMapper;
    private final Long organisationUnitId = 1L;

    public List<EncounterDTO> getAllEncounters() {
        return encounterMapper.toEncounterDTO(encounterRepository.findAllByArchived(UN_ARCHIVED));
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
        //FormData formData = new FormData();

        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setOrganisationUnitId(organisationUnitId);
        encounter = encounterRepository.save(encounter);

        //formData.setEncounterId(encounter.getId());
        //formData.setOrganisationalUnitId(organisationUnitId);
        //formDataRepository.save(formData);
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
}