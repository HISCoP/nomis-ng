package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrganisationUnitLevelDTO {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "status is mandatory")
    private Integer status;
}
