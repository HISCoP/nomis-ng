package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.HouseholdAddressDTO;
import org.nomisng.domain.entity.HouseholdAddress;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseholdAddressMapper {
    HouseholdAddress toHouseholdContact(HouseholdAddressDTO householdAddressDTO);

    List<HouseholdAddress> toHouseholdContacts(List<HouseholdAddressDTO> householdAddressDTOS);

    HouseholdAddressDTO toHouseholdContactDTO(HouseholdAddress householdAddress);

    List<HouseholdAddressDTO> toHouseholdContactDTOS(List<HouseholdAddress> householdAddresses);



}
