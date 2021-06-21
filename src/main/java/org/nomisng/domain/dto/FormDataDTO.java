package org.nomisng.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.JsonBEntity;
import org.nomisng.domain.entity.OrganisationUnit;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class FormDataDTO {
    private Long id;

    private Long encounterId;

    private Object data;
}
