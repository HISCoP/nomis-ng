package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Household {
    private Long id;
    private String uniqueId;
    private String status;
    private Long organisationalUnitId;
    private OrganisationUnit organisationUnitById;
    private Collection<HouseholdContact> householdContactsById;
    private Collection<HouseholdMember> householdMembersById;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "unique_id")
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "organisational_unit_id")
    public Long getOrganisationalUnitId() {
        return organisationalUnitId;
    }

    public void setOrganisationalUnitId(Long organisationalUnitId) {
        this.organisationalUnitId = organisationalUnitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Household household = (Household) o;
        return Objects.equals(id, household.id) &&
                Objects.equals(uniqueId, household.uniqueId) &&
                Objects.equals(status, household.status) &&
                Objects.equals(organisationalUnitId, household.organisationalUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueId, status, organisationalUnitId);
    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public OrganisationUnit getOrganisationUnitById() {
        return organisationUnitById;
    }

    public void setOrganisationUnitById(OrganisationUnit organisationUnitById) {
        this.organisationUnitById = organisationUnitById;
    }

    @OneToMany(mappedBy = "householdByHouseholdId")
    public Collection<HouseholdContact> getHouseholdContactsById() {
        return householdContactsById;
    }

    public void setHouseholdContactsById(Collection<HouseholdContact> householdContactsById) {
        this.householdContactsById = householdContactsById;
    }

    @OneToMany(mappedBy = "householdByHouseholdId")
    public Collection<HouseholdMember> getHouseholdMembersById() {
        return householdMembersById;
    }

    public void setHouseholdMembersById(Collection<HouseholdMember> householdMembersById) {
        this.householdMembersById = householdMembersById;
    }
}
