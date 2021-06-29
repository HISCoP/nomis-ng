package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

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
    @JsonIgnore
    private Long cboId = 1L;

    @ManyToOne
    @JoinColumn(name = "cbo_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public OrganisationUnit organisationUnitById;

    @OneToMany(mappedBy = "householdByHouseholdId")
    @ToString.Exclude
    @JsonIgnore
    public List<HouseholdContact> householdContacts;

    @OneToMany(mappedBy = "householdByHouseholdId")
    @ToString.Exclude
    @JsonIgnore
    public List<HouseholdMember> householdMembers;

    @OneToMany(mappedBy = "householdMemberByHouseholdMemberId")
    @ToString.Exclude
    @JsonIgnore
    private List<Encounter> encounterByOvcServiceCode;
}
