package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "organisation_unit")
public class OrganisationUnit extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "organisation_unit_level_id", nullable = false)
    private Long organisationUnitLevelId;

    @Basic
    @Column(name = "parent_organisation_unit_id", nullable = false)
    private Long parentOrganisationUnitId;

    @Basic
    @Column(name = "archived", nullable = false)
    private Integer archived = 0;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "latitude")
    private Double latitude;

    @Basic
    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "organisationUnitByCurrentOrganisationUnitId")
    @ToString.Exclude
    public Collection<ApplicationUser> applicationUsersById;

    @OneToMany(mappedBy = "organisationUnitByOrganisationalUnitId")
    @ToString.Exclude
    private Collection<FormData> formDataById;

    @OneToOne(mappedBy = "organisationUnitById")
    @ToString.Exclude
    private Household householdById;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_level_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    private OrganisationUnitLevel organisationUnitLevelByOrganisationUnitLevelId;

    @OneToMany(mappedBy = "organisationUnitByOrganisationUnitId")
    @ToString.Exclude
    private Collection<OrganisationUnitHierarchy> organisationUnitHierarchiesById;

    @OneToMany(mappedBy = "organisationUnitByParentOrganisationUnitId")
    @ToString.Exclude
    private Collection<OrganisationUnitHierarchy> parentOrganisationUnitHierarchiesById;

    @OneToMany(mappedBy = "organisationUnitByOrganisationUnitId")
    @ToString.Exclude
    private Collection<OrganisationUnitIdentifier> organisationUnitIdentifiersById;

    @OneToMany(mappedBy = "organisationUnitByCountryId")
    @ToString.Exclude
    private Collection<HouseholdContact> countryHouseholdContactsById;

    @OneToMany(mappedBy = "organisationUnitByStateId")
    @ToString.Exclude
    private Collection<HouseholdContact> stateHouseholdContactsById;

    @OneToMany(mappedBy = "organisationUnitByProvinceId")
    @ToString.Exclude
    private Collection<HouseholdContact> provinceHouseholdContactsById;
}
