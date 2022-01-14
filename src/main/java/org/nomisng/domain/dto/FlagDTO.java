package org.nomisng.domain.dto;

import lombok.Data;


@Data
public class FlagDTO {

    private Long id;

    private String name;

    private String fieldName;

    private String fieldValue;

    private Integer datatype;

    private String operator;

    private Boolean continuous;
}