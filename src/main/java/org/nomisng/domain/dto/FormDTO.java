package org.nomisng.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.domain.entity.JsonBEntity;
import org.nomisng.domain.entity.Service;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class FormDTO extends JsonBEntity {

    private Long id;

    private String name;

    private String code;

    private Object resourceObject;

    private Long formTypeId;

    private Long resourcePath;

    private Long serviceId;

    private String serviceName;
}
