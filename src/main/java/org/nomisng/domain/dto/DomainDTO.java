package org.nomisng.domain.dto;

import lombok.Data;


@Data
public class DomainDTO {

    private Long id;

    private String name;

    private String code;

    private int archived;
}
