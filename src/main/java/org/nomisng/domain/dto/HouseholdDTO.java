package org.nomisng.domain.dto;

import lombok.Data;
import org.nomisng.domain.entity.*;
import java.util.List;

@Data
public class HouseholdDTO {

    private Long id;

    private String uniqueId;

    private String status;

    private Long cboId;

    private List<HouseholdContactDTO> householdContactDTOS;

    private HouseholdMemberDTO householdMemberDTO;

    private Object details;
}
