package org.nomisng.domain.dto;

import lombok.Data;

@Data
public class OvcServiceDTO {

    private Long id;

    private String name;

    private String code;

    private Long domainId;

    private Integer archived;

    private int serviceType;
}
