package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.nomisng.util.converter.LocalDateConverter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@EqualsAndHashCode
@Table(name = "household_member")
public class HouseholdMember extends JsonBEntity {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Basic
    @Column(name = "dob")
    @Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Basic
    @Column(name = "dob_estimated", nullable = false)
    private Boolean dobEstimated;

    @Basic
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Basic
    @Column(name = "gender_id", nullable = false)
    private Long genderId;

    @Basic
    @Column(name = "marital_status_id", nullable = false)
    private Long maritalStatusId;

    @Basic
    @Column(name = "education_id", nullable = false)
    private Long educationId;

    @Basic
    @Column(name = "occupation_id")
    private Long occupationId;*/

    @Basic
    @Column(name = "household_id")
    private Long householdId;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "details", nullable = false, columnDefinition = "jsonb")
    private Object details;

    @Basic
    @Column(name = "household_member_type") //1 - Caregiver, 2 - OVC & Caregiver, 3 OVC, 4 - Other members
    private Integer householdMemberType;

    @Basic
    @Column(name = "archived")
    private int archived;

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public Household householdByHouseholdId;

    /*@ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public ApplicationCodeset applicationCodesetByGenderId;

    @ManyToOne
    @JoinColumn(name = "marital_status_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public ApplicationCodeset applicationCodesetByMaritalStatusId;

    @ManyToOne
    @JoinColumn(name = "education_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public ApplicationCodeset applicationCodesetByEducationId;

    @ManyToOne
    @JoinColumn(name = "occupation_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public ApplicationCodeset applicationCodesetByOccupationId;*/


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

    @OneToMany(mappedBy = "householdMemberByHouseholdMemberId")
    @ToString.Exclude
    @JsonIgnore
    private List<Encounter> encounterByHouseholdMemberId;
}
