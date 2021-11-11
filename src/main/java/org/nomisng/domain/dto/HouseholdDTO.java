package org.nomisng.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode
public class HouseholdDTO {

    private Long id;

    private String uniqueId;

    private String status;

    private Long cboId;

    private Long organisationUnitId;

    @NotNull(message = "householdMigrationDTOS is mandatory")
    private List<HouseholdMigrationDTO> householdMigrationDTOS;

    @NotNull(message = "householdMemberDTO is mandatory")
    private HouseholdMemberDTO householdMemberDTO;

    private Object details;

    private Object assessment;
}
