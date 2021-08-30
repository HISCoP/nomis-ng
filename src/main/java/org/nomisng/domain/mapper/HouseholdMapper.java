package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.nomisng.domain.dto.HouseholdAddressDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdAddress;
import org.nomisng.domain.entity.HouseholdMember;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseholdMapper {
    Household toHousehold(HouseholdDTO householdDTO);

    HouseholdDTO toHouseholdDTO(Household household);

    List<HouseholdDTO> toHouseholdDTOS(List<Household> households);

    /*@Mappings({
            @Mapping(source="household.id", target="id")
    })
    HouseholdDTO toHouseholdDTO(Household household, List<HouseholdMember> householdMembers, List<HouseholdAddress> householdAddresses);
*/
    @Mappings({
            @Mapping(source="household.id", target="id")
    })
    HouseholdDTO toHouseholdDTO(Household household, List<HouseholdMemberDTO> householdMemberDTOS, List<HouseholdAddressDTO> householdAddressDTOS);


    @Mappings({
            @Mapping(source="householdDTO.id", target="id")
    })
    Household toHousehold(HouseholdDTO householdDTO, List<HouseholdMemberDTO> householdMemberDTOS, HouseholdAddressDTO householdAddressDTO);

    @Mappings({
            @Mapping(source="householdDTO.id", target="id")
    })
    Household toHousehold(HouseholdDTO householdDTO, List<HouseholdMember> householdMembers, List<HouseholdAddress> householdAddresses);

}
