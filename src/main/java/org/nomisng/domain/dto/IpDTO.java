package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IpDTO {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    private String description;

    private String code;

    //private Integer archived = 0;
}
