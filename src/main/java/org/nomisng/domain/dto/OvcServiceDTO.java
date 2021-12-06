package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OvcServiceDTO {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    private String code;

    @NotNull(message = "domainId is mandatory")
    private Long domainId;

    private Integer archived;

    @NotNull(message = "serviceType is mandatory")
    private Integer serviceType;

    private String domainName;
}
