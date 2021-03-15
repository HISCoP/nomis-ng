package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "organisation_unit_level", schema = "public", catalog = "nomis")
public class OrganisationUnitLevel {
    private Long id;
    private String name;
    private String description;
    private Integer archived;
    private Integer status;
    private Collection<OrganisationUnit> organisationUnitsById;
    private Collection<OrganisationUnitHierarchy> organisationUnitHierarchiesById;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "archived")
    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationUnitLevel that = (OrganisationUnitLevel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(archived, that.archived) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, archived, status);
    }

    @OneToMany(mappedBy = "organisationUnitLevelByOrganisationUnitLevelId")
    public Collection<OrganisationUnit> getOrganisationUnitsById() {
        return organisationUnitsById;
    }

    public void setOrganisationUnitsById(Collection<OrganisationUnit> organisationUnitsById) {
        this.organisationUnitsById = organisationUnitsById;
    }

    @OneToMany(mappedBy = "organisationUnitLevelByOrganisationUnitLevelId")
    public Collection<OrganisationUnitHierarchy> getOrganisationUnitHierarchiesById() {
        return organisationUnitHierarchiesById;
    }

    public void setOrganisationUnitHierarchiesById(Collection<OrganisationUnitHierarchy> organisationUnitHierarchiesById) {
        this.organisationUnitHierarchiesById = organisationUnitHierarchiesById;
    }
}
