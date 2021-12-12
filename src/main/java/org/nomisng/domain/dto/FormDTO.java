package org.nomisng.domain.dto;

import lombok.Data;
import org.nomisng.domain.entity.JsonBEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FormDTO {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    private String code;

    private Object resourceObject;

    private String resourcePath;

    private String supportServices;

    @NotBlank(message = "version is mandatory")
    private String version;

    private int archived;
}
