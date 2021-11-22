package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

@Data
public class CboProjectDTO {

    private Long id;

    @NotNull(message = "cboId is mandatory")
    private Long cboId;

    @NotNull(message = "donorId is mandatory")
    private Long donorId;

    @NotNull(message = "implementerId is mandatory")
    private Long implementerId;

    @NotNull(message = "organisationUnitIds is mandatory")
    private List<Long> organisationUnitIds;

    private String cboName;

    private String donorName;

    private String implementerName;

    private String organisationUnitName;

    private String description;

    private List organisationUnits;
}
