package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "organisation_unit_hierarchy", schema = "public", catalog = "nomis")
public class OrganisationUnitHierarchy {
    private Long id;
    private Long organisationUnitId;
    private Long parentOrganisationUnitId;
    private Long organisationUnitLevelId;
    private OrganisationUnit organisationUnitByOrganisationUnitId;
    private OrganisationUnit organisationUnitByParentOrganisationUnitId;
    private OrganisationUnitLevel organisationUnitLevelByOrganisationUnitLevelId;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "organisation_unit_id")
    public Long getOrganisationUnitId() {
        return organisationUnitId;
    }

    public void setOrganisationUnitId(Long organisationUnitId) {
        this.organisationUnitId = organisationUnitId;
    }

    @Basic
    @Column(name = "parent_organisation_unit_id")
    public Long getParentOrganisationUnitId() {
        return parentOrganisationUnitId;
    }

    public void setParentOrganisationUnitId(Long parentOrganisationUnitId) {
        this.parentOrganisationUnitId = parentOrganisationUnitId;
    }

    @Basic
    @Column(name = "organisation_unit_level_id")
    public Long getOrganisationUnitLevelId() {
        return organisationUnitLevelId;
    }

    public void setOrganisationUnitLevelId(Long organisationUnitLevelId) {
        this.organisationUnitLevelId = organisationUnitLevelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationUnitHierarchy that = (OrganisationUnitHierarchy) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(organisationUnitId, that.organisationUnitId) &&
                Objects.equals(parentOrganisationUnitId, that.parentOrganisationUnitId) &&
                Objects.equals(organisationUnitLevelId, that.organisationUnitLevelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organisationUnitId, parentOrganisationUnitId, organisationUnitLevelId);
    }

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id")
    public OrganisationUnit getOrganisationUnitByOrganisationUnitId() {
        return organisationUnitByOrganisationUnitId;
    }

    public void setOrganisationUnitByOrganisationUnitId(OrganisationUnit organisationUnitByOrganisationUnitId) {
        this.organisationUnitByOrganisationUnitId = organisationUnitByOrganisationUnitId;
    }

    @ManyToOne
    @JoinColumn(name = "parent_organisation_unit_id", referencedColumnName = "id")
    public OrganisationUnit getOrganisationUnitByParentOrganisationUnitId() {
        return organisationUnitByParentOrganisationUnitId;
    }

    public void setOrganisationUnitByParentOrganisationUnitId(OrganisationUnit organisationUnitByParentOrganisationUnitId) {
        this.organisationUnitByParentOrganisationUnitId = organisationUnitByParentOrganisationUnitId;
    }

    @ManyToOne
    @JoinColumn(name = "organisation_unit_level_id", referencedColumnName = "id")
    public OrganisationUnitLevel getOrganisationUnitLevelByOrganisationUnitLevelId() {
        return organisationUnitLevelByOrganisationUnitLevelId;
    }

    public void setOrganisationUnitLevelByOrganisationUnitLevelId(OrganisationUnitLevel organisationUnitLevelByOrganisationUnitLevelId) {
        this.organisationUnitLevelByOrganisationUnitLevelId = organisationUnitLevelByOrganisationUnitLevelId;
    }
}
