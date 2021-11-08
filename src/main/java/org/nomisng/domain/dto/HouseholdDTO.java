package org.nomisng.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class HouseholdDTO {

    private Long id;

    private String uniqueId;

    private String status;

    private Long cboId;

    private List<HouseholdAddressDTO> householdAddressDTOS;

    private HouseholdMemberDTO householdMemberDTO;

    private Object details;
}
