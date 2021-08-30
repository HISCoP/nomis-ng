package org.nomisng.domain.dto;

import lombok.Data;

@Data
public class IpDTO {

    private Long id;

    private String name;

    private String description;

    private String code;

    private Integer archived = 0;
}
