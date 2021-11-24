package org.nomisng.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.nomisng.domain.entity.OrganisationUnit;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode
public class HouseholdDTO {

    private Long id;

    private String uniqueId;

    private String status;

    private Long cboId;

    @NotNull(message = "householdMigrationDTOS is mandatory")
    private List<HouseholdMigrationDTO> householdMigrationDTOS;

    @NotNull(message = "householdMemberDTO is mandatory")
    private HouseholdMemberDTO householdMemberDTO;

    private Object details;

    private Object assessment;

    private OrganisationUnit ward;

    private Long wardId;

    private Long serial_number;
}
