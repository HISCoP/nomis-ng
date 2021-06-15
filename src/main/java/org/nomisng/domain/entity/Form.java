package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "form")
public class Form extends JsonBEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    @NotNull(message = "name cannot be null")
    private String name;

    @Basic
    @Column(name = "code")
    private String code;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resource_object", nullable = false, columnDefinition = "jsonb")
    private Object resourceObject;

    @Basic
    @Column(name = "form_type_id")
    private int formTypeId;

    @Basic
    @Column(name = "resource_path")
    private String resourcePath;

    @Basic
    @Column(name = "service_code")
    private String programCode;

    @Basic
    @Column(name = "archived")
    private Integer archived = 0;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private String createdBy = "guest@nomisng.org";

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateCreated = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonIgnore
    @ToString.Exclude
    private String modifiedBy = "guest@nomisng.org";

    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateModified = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "service_code", referencedColumnName = "code", updatable = false, insertable = false)
    @JsonIgnore
    private Program programByProgramCode;
}
