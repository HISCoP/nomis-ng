package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class ReportInfoDTO {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    private String description;

    private String reportFormat;

    @NotBlank(message = "template is mandatory")
    private String template;

    private Object resourceObject;
}
