package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
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


    @OneToMany(mappedBy = "organisationUnitByCurrentOrganisationUnitId")
    @ToString.Exclude
    public Collection<User> UsersById;

    @OneToMany(mappedBy = "organisationUnitByOrganisationalUnitId")
    @ToString.Exclude
    private Collection<FormData> formDataById;

    @OneToMany(mappedBy = "organisationUnitById")
    @ToString.Exclude
    private List<Household> households;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_level_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
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
