package org.nomisng.domain.dto;

import lombok.Data;
import org.nomisng.domain.entity.JsonBEntity;

@Data
public class FormDTO extends JsonBEntity {

    private Long id;

    private String name;

    private String code;

    private Object resourceObject;

    private Long formTypeId;

    private String resourcePath;

    private String ovcServiceCode;

    private String ovcServiceName;

    private String version;
}
