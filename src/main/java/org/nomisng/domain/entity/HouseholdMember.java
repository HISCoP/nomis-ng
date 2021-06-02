package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import javax.validation.constraints.NotNull;



@Entity
@Data
@EqualsAndHashCode
@Table(name = "household_member")
public class HouseholdMember extends Audit {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "dob", nullable = false)
    private Date dob;

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
    @Column(name = "occupation_id", nullable = false)
    private Long occupationId;

    @Basic
    @Column(name = "household_id", nullable = false)
    private Long householdId;

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public Household householdByHouseholdId;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public ApplicationCodeset applicationCodesetByGenderId;

    @ManyToOne
    @JoinColumn(name = "marital_status_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public ApplicationCodeset applicationCodesetByMaritalStatusId;

    @ManyToOne
    @JoinColumn(name = "education_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public ApplicationCodeset applicationCodesetByEducationId;

    @ManyToOne
    @JoinColumn(name = "occupation_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public ApplicationCodeset applicationCodesetByOccupationId;
}
