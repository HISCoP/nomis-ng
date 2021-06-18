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
@Table(name = "household")
public class Household extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "unique_id")
    private String uniqueId;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "cbo_id")
    private Long cboId;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public OrganisationUnit organisationUnitById;

    @OneToMany(mappedBy = "householdByHouseholdId")
    @ToString.Exclude
    public Collection<HouseholdContact> householdContactsById;

    @OneToMany(mappedBy = "householdByHouseholdId")
    @ToString.Exclude
    public Collection<HouseholdMember> householdMembersById;

    @JsonIgnore
    @OneToMany(mappedBy = "householdMemberByHouseholdMemberId")
    private List<Encounter> encounterByOvcServiceCode;
}
