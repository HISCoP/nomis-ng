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

    //TODO: discuss on changing status to an int
    @Basic
    @Column(name = "status") // 0 - registered, 1 - registered and assessed, 2 - graduated
    private String status;

    @Basic
    @Column(name = "cbo_id")
    @JsonIgnore
    private Long cboId = 1L;

    @Basic
    @Column(name = "archived")
    @JsonIgnore
    private int archived;

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
}
