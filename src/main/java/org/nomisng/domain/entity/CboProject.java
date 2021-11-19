package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "cbo_project")
public class CboProject {

    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    @ToString.Exclude
    public List<ApplicationUserCboProject> applicationUserCboProjectById;
    @OneToMany(mappedBy = "cboProjectByCurrentCboProjectId")
    @JsonIgnore
    @ToString.Exclude
    public List<User> users;
    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    @ToString.Exclude
    public List<Household> households;
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "cbo_id")
    private Long cboId;
    @Basic
    @Column(name = "donor_id")
    private Long donorId;
    @Basic
    @Column(name = "implementer_id")
    private Long implementerId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "archived")
    private Integer archived;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "cbo_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Cbo cboByCboId;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "donor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Donor donorByDonorId;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "implementer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Implementer implementerByImplementerId;
    @Transient
    private String cboName;
    @Transient
    private String donorName;
    @Transient
    private String implementerName;
    @Transient
    private String organisationUnitName;
    @Transient
    private List organisationUnits;

    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @ToString.Exclude
    private List<CboProjectLocation> cboProjectLocationsById;

    @OneToMany(mappedBy = "cboProjectByCboId")
    @ToString.Exclude
    private List<User> usersById;

    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    @ToString.Exclude
    private List<HouseholdMigration> householdMigrationsById;

    @OneToMany(mappedBy = "householdMemberByCboProjectId")
    @JsonIgnore
    @ToString.Exclude
    private List<HouseholdMember> householdMembersById;

    @OneToMany(mappedBy = "encounterByCboProjectId", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Encounter> encountersById;

    @OneToMany(mappedBy = "cboProjectByCboProjectId", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<FormData> formData;
}
