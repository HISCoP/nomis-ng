package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.nomisng.domain.dto.HouseholdContactDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdContact;
import org.nomisng.domain.entity.HouseholdMember;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseholdMemberMapper {
    HouseholdMember toHouseholdMember(HouseholdMemberDTO householdMemberDTO);

    List<HouseholdMember> toHouseholdMembers(List<HouseholdMemberDTO> householdMemberDTO);


    HouseholdMemberDTO toHouseholdMemberDTO(HouseholdMember householdMember);

    List<HouseholdMemberDTO> toHouseholdMemberDTOS(List<HouseholdMember> householdMembers);


    List<HouseholdMemberDTO> toHouseholdDTOS(List<HouseholdMember> householdMembers);
}
