package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "organisation_unit_identifier", schema = "public", catalog = "nomis")
public class OrganisationUnitIdentifier {
    private Long id;
    private Long organisationUnitId;
    private String code;
    private String name;
    private OrganisationUnit organisationUnitByOrganisationUnitId;

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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationUnitIdentifier that = (OrganisationUnitIdentifier) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(organisationUnitId, that.organisationUnitId) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organisationUnitId, code, name);
    }

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id")
    public OrganisationUnit getOrganisationUnitByOrganisationUnitId() {
        return organisationUnitByOrganisationUnitId;
    }

    public void setOrganisationUnitByOrganisationUnitId(OrganisationUnit organisationUnitByOrganisationUnitId) {
        this.organisationUnitByOrganisationUnitId = organisationUnitByOrganisationUnitId;
    }
}
