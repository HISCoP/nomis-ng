package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "organisation_unit_identifier")
public class OrganisationUnitIdentifier extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "organisation_unit_id", nullable = false)
    private Long organisationUnitId;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganisationUnit organisationUnitByOrganisationUnitId;

    @PrePersist
    public void update() {
        if(this.code == null || this.code.isEmpty()) {
            this.code = UUID.randomUUID().toString();
        }
    }
}
