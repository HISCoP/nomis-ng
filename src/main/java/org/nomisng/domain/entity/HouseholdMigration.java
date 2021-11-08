package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "household_migration")
public class HouseholdMigration extends Audit {

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
    @Column(name = "household_id")
    private Long householdId;

    @Basic
    @Column(name = "active", nullable = false)
    private Integer active;

    @Basic
    @Column(name = "organisation_unit_id", nullable = false)
    private Long organisationUnitId;

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public Household householdByHouseholdId;

    @PrePersist
    public void persist() {
        if(this.householdByHouseholdId != null && this.householdByHouseholdId.getId() != null) {
            this.householdId = householdByHouseholdId.getId();
        }
    }

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    private OrganisationUnit organisationUnitByOrganisationUnitId;
}
