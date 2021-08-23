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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "household")
public class Household extends JsonBEntity {
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

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "details", nullable = false, columnDefinition = "jsonb")
    private Object details;

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
}
