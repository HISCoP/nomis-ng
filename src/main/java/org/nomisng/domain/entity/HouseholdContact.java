package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "household_contact")
public class HouseholdContact {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @Basic
    @Column(name = "alternate_phone_number")
    private String alternatePhoneNumber;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "zip_code")
    private String zipCode;

    @Basic
    @Column(name = "city")
    private String city;

    @Basic
    @Column(name = "street")
    private String street;

    @Basic
    @Column(name = "landmark")
    private String landmark;

    @Basic
    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @Basic
    @Column(name = "state_id", nullable = false)
    private Long stateId;

    @Basic
    @Column(name = "province_id", nullable = false)
    private Long provinceId;

    @Basic
    @Column(name = "household_id", nullable = false)
    private Long householdId;

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public Household householdByHouseholdId;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public OrganisationUnit organisationUnitByCountryId;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    private OrganisationUnit organisationUnitByStateId;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    private OrganisationUnit organisationUnitByProvinceId;
}
