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
@Table(name = "form_data")
public class FormData extends JsonBEntity{

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "encounter_id")
    private Long encounterId;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    private Object data;

    @Basic
    @Column(name = "organisational_unit_id")
    private Long organisationalUnitId;

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
    @JoinColumn(name = "encounter_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public Encounter encounterByEncounterId;

    @ManyToOne
    @JoinColumn(name = "organisational_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public OrganisationUnit organisationUnitByOrganisationalUnitId;
}
