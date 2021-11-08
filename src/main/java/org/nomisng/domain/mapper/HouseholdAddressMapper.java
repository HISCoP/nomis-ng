package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.HouseholdAddressDTO;
import org.nomisng.domain.entity.HouseholdMigration;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseholdAddressMapper {
    HouseholdMigration toHouseholdContact(HouseholdAddressDTO householdAddressDTO);

    List<HouseholdMigration> toHouseholdContacts(List<HouseholdAddressDTO> householdAddressDTOS);

    HouseholdAddressDTO toHouseholdContactDTO(HouseholdMigration householdMigration);

    List<HouseholdAddressDTO> toHouseholdContactDTOS(List<HouseholdMigration> householdMigrations);



}
