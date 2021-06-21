package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Form;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EncounterMapper {
    Encounter toEncounter(EncounterDTO encounterDTO);
    @Mappings({
            @Mapping(source="encounter.id", target="id"),
            @Mapping(source="encounter.ovcServiceCode", target="ovcServiceCode")
    })
    EncounterDTO toEncounterDTO(Encounter encounter, Form form);

    List<Encounter> toEncounters(List<EncounterDTO> encounterDTOS);

    List<EncounterDTO> toEncounterDTO(List<Encounter> encounters);

    EncounterDTO toEncounterDTO(Encounter encounter);



}
