package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.nomisng.util.converter.LocalDateConverter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
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
    private Long occupationId;

    @Basic
    @Column(name = "household_id")
    private Long householdId;

    @Basic
    @Column(name = "household_member_type") //1 - OVC, 2 - Caregiver, 3 OVC & Caregiver
    private Integer householdMemberType;

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    public Household householdByHouseholdId;

    @ManyToOne
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
    public ApplicationCodeset applicationCodesetByOccupationId;

    /*@PrePersist
    public void update() {
        if(this.householdByHouseholdId != null && this.householdByHouseholdId.getId() != null) {
            this.householdId = householdByHouseholdId.getId();
        }
    }*/
}
