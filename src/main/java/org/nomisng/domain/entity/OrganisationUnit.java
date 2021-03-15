package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "organisation_unit", schema = "public", catalog = "nomis")
public class OrganisationUnit {
    private Long id;
    private String name;
    private String description;
    private Long organisationUnitLevelId;
    private Long parentOrganisationUnitId;
    private Integer archived;
    private String address;
    private String phone;
    private String email;
    private Double latitude;
    private Double longitude;
    private Collection<ApplicationUser> applicationUsersById;
    private Collection<FormData> formDataById;
    private Household householdById;
    private OrganisationUnitLevel organisationUnitLevelByOrganisationUnitLevelId;
    private Collection<OrganisationUnitHierarchy> organisationUnitHierarchiesById;
    private Collection<OrganisationUnitHierarchy> organisationUnitHierarchiesById_0;
    private Collection<OrganisationUnitIdentifier> organisationUnitIdentifiersById;

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
    @Column(name = "organisation_unit_level_id")
    public Long getOrganisationUnitLevelId() {
        return organisationUnitLevelId;
    }

    public void setOrganisationUnitLevelId(Long organisationUnitLevelId) {
        this.organisationUnitLevelId = organisationUnitLevelId;
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
    @Column(name = "archived")
    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationUnit that = (OrganisationUnit) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(organisationUnitLevelId, that.organisationUnitLevelId) &&
                Objects.equals(parentOrganisationUnitId, that.parentOrganisationUnitId) &&
                Objects.equals(archived, that.archived) &&
                Objects.equals(address, that.address) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, organisationUnitLevelId, parentOrganisationUnitId, archived, address, phone, email, latitude, longitude);
    }

    @OneToMany(mappedBy = "organisationUnitByCurrentOrganisationUnitId")
    public Collection<ApplicationUser> getApplicationUsersById() {
        return applicationUsersById;
    }

    public void setApplicationUsersById(Collection<ApplicationUser> applicationUsersById) {
        this.applicationUsersById = applicationUsersById;
    }

    @OneToMany(mappedBy = "organisationUnitByOrganisationalUnitId")
    public Collection<FormData> getFormDataById() {
        return formDataById;
    }

    public void setFormDataById(Collection<FormData> formDataById) {
        this.formDataById = formDataById;
    }

    @OneToOne(mappedBy = "organisationUnitById")
    public Household getHouseholdById() {
        return householdById;
    }

    public void setHouseholdById(Household householdById) {
        this.householdById = householdById;
    }

    @ManyToOne
    @JoinColumn(name = "organisation_unit_level_id", referencedColumnName = "id")
    public OrganisationUnitLevel getOrganisationUnitLevelByOrganisationUnitLevelId() {
        return organisationUnitLevelByOrganisationUnitLevelId;
    }

    public void setOrganisationUnitLevelByOrganisationUnitLevelId(OrganisationUnitLevel organisationUnitLevelByOrganisationUnitLevelId) {
        this.organisationUnitLevelByOrganisationUnitLevelId = organisationUnitLevelByOrganisationUnitLevelId;
    }

    @OneToMany(mappedBy = "organisationUnitByOrganisationUnitId")
    public Collection<OrganisationUnitHierarchy> getOrganisationUnitHierarchiesById() {
        return organisationUnitHierarchiesById;
    }

    public void setOrganisationUnitHierarchiesById(Collection<OrganisationUnitHierarchy> organisationUnitHierarchiesById) {
        this.organisationUnitHierarchiesById = organisationUnitHierarchiesById;
    }

    @OneToMany(mappedBy = "organisationUnitByParentOrganisationUnitId")
    public Collection<OrganisationUnitHierarchy> getOrganisationUnitHierarchiesById_0() {
        return organisationUnitHierarchiesById_0;
    }

    public void setOrganisationUnitHierarchiesById_0(Collection<OrganisationUnitHierarchy> organisationUnitHierarchiesById_0) {
        this.organisationUnitHierarchiesById_0 = organisationUnitHierarchiesById_0;
    }

    @OneToMany(mappedBy = "organisationUnitByOrganisationUnitId")
    public Collection<OrganisationUnitIdentifier> getOrganisationUnitIdentifiersById() {
        return organisationUnitIdentifiersById;
    }

    public void setOrganisationUnitIdentifiersById(Collection<OrganisationUnitIdentifier> organisationUnitIdentifiersById) {
        this.organisationUnitIdentifiersById = organisationUnitIdentifiersById;
    }
}
