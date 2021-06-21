package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Form;

@Mapper(componentModel = "spring")
public interface EncounterMapper {
    Encounter toEncounter(EncounterDTO encounterDTO);
    @Mappings({
            @Mapping(source="encounter.id", target="encounterId"),
            @Mapping(source="encounter.organisationUnitId", target="organisationUnitId")
    })
    EncounterDTO toEncounterDTO(Encounter encounter, Form form);
}
