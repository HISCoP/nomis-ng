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
import java.sql.Timestamp;
import java.util.Objects;

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
    private Long formTypeId;

    @Basic
    @Column(name = "resource_path")
    private Long resourcePath;

    @Basic
    @Column(name = "service_id")
    private Long serviceId;

    @Basic
    @Column(name = "archived")
    private int archived;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private String createdBy;

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private Timestamp dateCreated;

    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonIgnore
    @ToString.Exclude
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonIgnore
    @ToString.Exclude
    private Timestamp dateModified;

    @ManyToOne
    @JoinColumn(name = "form_type_id", referencedColumnName = "id", updatable = false, insertable = false)
    private ApplicationCodeset applicationCodesetByFormTypeId;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Service serviceByServiceId;
}
