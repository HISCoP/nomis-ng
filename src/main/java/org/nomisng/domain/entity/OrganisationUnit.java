package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "organisation_unit")
public class OrganisationUnit extends JsonBEntity implements Serializable {

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "organisation_unit_level_id", referencedColumnName = "id", updatable = false, insertable = false)
    public OrganisationUnitLevel organisationUnitLevelByOrganisationUnitLevelId;

    @OneToMany(mappedBy = "organisationUnitByOrganisationUnitId")
    @JsonIgnore
    @ToString.Exclude
    public List<OrganisationUnitHierarchy> organisationUnitHierarchiesById;

    @OneToMany(mappedBy = "organisationUnitByParentOrganisationUnitId")
    @JsonIgnore
    @ToString.Exclude
    public List<OrganisationUnitHierarchy> organisationUnitHierarchiesById_0;

/*    @OneToMany(mappedBy = "organisationUnitByCurrentOrganisationUnitId")
    @JsonIgnore
    @ToString.Exclude
    public List<User> users;*/
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
    /*@OneToMany(mappedBy = "organisationUnitByOrganisationalUnitId")
    @JsonIgnore
    @ToString.Exclude
    public List<FormData> formDataById;*/
    @Basic
    @Column(name = "organisation_unit_level_id")
    private Long organisationUnitLevelId;
    @Basic
    @Column(name = "parent_organisation_unit_id")
    private Long parentOrganisationUnitId;
    @Basic
    @Column(name = "archived")
    @JsonIgnore
    private Integer archived = 0;
    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "details", nullable = false, columnDefinition = "jsonb")
    private Object details;
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private String createdBy = "guest@nomisng.org";//SecurityUtils.getCurrentUserLogin().orElse(null);
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateCreated = LocalDateTime.now();
    @Column(name = "modified_by")
    @JsonIgnore
    @ToString.Exclude
    private String modifiedBy = "guest@nomisng.org";//SecurityUtils.getCurrentUserLogin().orElse(null);

    @Column(name = "date_modified")
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateModified = LocalDateTime.now();

    @Transient
    private String  parentOrganisationUnitName;

    @Transient
    private String  parentParentOrganisationUnitName;

    @Transient
    private Long householdMaxCount;

    @OneToMany(mappedBy = "organisationUnitByOrganisationUnitId")
    @JsonIgnore
    @ToString.Exclude
    private List<Household>householdsById;


    @OneToMany(mappedBy = "organisationUnitByOrganisationUnitId")
    @JsonIgnore
    @ToString.Exclude
    private List<CboProjectLocation> cboProjectLocationsById;
}
