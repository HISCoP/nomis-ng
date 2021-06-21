package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "application_codeset")
public class ApplicationCodeset extends Audit {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "codeset_group")
    @NotNull(message = "codesetGroup cannot be null")
    private String codesetGroup;

    @Basic
    @Column(name = "display")
    @NotNull(message = "display cannot be null")
    private String display;

    @Basic
    @Column(name = "language")
    @NotNull(message = "language cannot be null")
    private String language;

    @Basic
    @Column(name = "version")
    private String version = "1.0";

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "archived")
    private Integer archived = 0;

    @OneToMany(mappedBy = "applicationCodesetByGenderId")
    @ToString.Exclude
    @JsonIgnore
    private Collection<HouseholdMember> householdMembersByGenderId;

    @OneToMany(mappedBy = "applicationCodesetByMaritalStatusId")
    @ToString.Exclude
    @JsonIgnore
    private Collection<HouseholdMember> householdMembersByMaritalStatusId;

    @OneToMany(mappedBy = "applicationCodesetByEducationId")
    @ToString.Exclude
    @JsonIgnore
    public Collection<HouseholdMember> householdMembersByEducationId;

    @OneToMany(mappedBy = "applicationCodesetByOccupationId")
    @ToString.Exclude
    @JsonIgnore
    private Collection<HouseholdMember> householdMembersByOccupationId;

    @OneToMany(mappedBy = "applicationCodesetByApplicationCodesetId")
    @ToString.Exclude
    @JsonIgnore
    private Collection<ApplicationCodesetStandardCodeset> applicationCodesetStandardCodesetsById;

    @OneToMany(mappedBy = "applicationCodesetByFormTypeId")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Form> formsById;
}
