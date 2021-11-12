package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CboDTO {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    private String description;

    private String code;
}
