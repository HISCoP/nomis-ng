package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "household")
public class Household extends JsonBEntity implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "unique_id")
    private String uniqueId;

    @Basic
    @Column(name = "status") // 1  - active, 2 - graduated
    private int status;

    @Basic
    @Column(name = "cbo_id")
    @JsonIgnore
    private Long cboId = 1L;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "details", nullable = false, columnDefinition = "jsonb")
    private Object details;

    @Basic
    @Column(name = "archived")
    @JsonIgnore
    private int archived;

    @Basic
    @Column(name = "organisation_unit_id")
    @JsonIgnore
    private Long organisationUnitId;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private String createdBy = "guest@nomisng.org";/*SecurityUtils.getCurrentUserLogin().orElse(null);*/

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateCreated = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonIgnore
    @ToString.Exclude
    private String modifiedBy = "guest@nomisng.org";//SecurityUtils.getCurrentUserLogin().orElse(null);

    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateModified = LocalDateTime.now();

    @OneToMany(mappedBy = "householdByHouseholdId")
    @JsonIgnore
    @ToString.Exclude
    private List<Encounter> encountersById;

    @ManyToOne
    @JoinColumn(name = "cbo_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    private OrganisationUnit organisationUnitById;

    @OneToMany(mappedBy = "householdByHouseholdId")
    @ToString.Exclude
    @JsonIgnore
    private List<HouseholdMigration> householdMigrations;

    @OneToMany(mappedBy = "householdByHouseholdId")
    @ToString.Exclude
    @JsonIgnore
    private List<HouseholdMember> householdMembers;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    private OrganisationUnit organisationUnitByOrganisationUnitId;

    @OneToMany(mappedBy = "householdByHouseholdId")
    private Collection<HouseholdMigration> householdMigrationsById;
}
