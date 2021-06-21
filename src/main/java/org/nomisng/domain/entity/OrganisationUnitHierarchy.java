package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "organisation_unit_hierarchy")
public class OrganisationUnitHierarchy {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "organisation_unit_id")
    private Long organisationUnitId;

    @Basic
    @Column(name = "parent_organisation_unit_id")
    private Long parentOrganisationUnitId;

    @Basic
    @Column(name = "organisation_unit_level_id")
    private Long organisationUnitLevelId;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    private OrganisationUnit organisationUnitByOrganisationUnitId;

    @ManyToOne
    @JoinColumn(name = "parent_organisation_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    private OrganisationUnit organisationUnitByParentOrganisationUnitId;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_level_id", referencedColumnName = "id",updatable = false, insertable = false)
    @ToString.Exclude
    private OrganisationUnitLevel organisationUnitLevelByOrganisationUnitLevelId;
}
