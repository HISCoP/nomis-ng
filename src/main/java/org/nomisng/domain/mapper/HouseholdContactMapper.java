package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.HouseholdContactDTO;
import org.nomisng.domain.entity.HouseholdContact;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseholdContactMapper {
    HouseholdContact toHouseholdContact(HouseholdContactDTO householdContactDTO);

    List<HouseholdContact> toHouseholdContacts(List<HouseholdContactDTO> householdContactDTOS);


    HouseholdContactDTO toHouseholdContactDTO(HouseholdContact householdContact);

    List<HouseholdContactDTO> toHouseholdContactDTOS(List<HouseholdContact> householdContacts);



}
