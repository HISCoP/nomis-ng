package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "household_address")
public class HouseholdAddress extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(name = "household_id")
    private Long householdId;

    @Basic
    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public Household householdByHouseholdId;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public OrganisationUnit organisationUnitByCountryId;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    private OrganisationUnit organisationUnitByStateId;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    private OrganisationUnit organisationUnitByProvinceId;

    @PrePersist
    public void persist() {
        if(this.householdByHouseholdId != null && this.householdByHouseholdId.getId() != null) {
            this.householdId = householdByHouseholdId.getId();
        }
    }
}
