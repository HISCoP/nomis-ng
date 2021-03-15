package org.nomisng.domain.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "household_member", schema = "public", catalog = "nomis")
public class HouseholdMember {
    private Long id;
    private Date dob;
    private Boolean dobEstimated;
    private String firstName;
    private String lastName;
    private Long genderId;
    private Long maritalStatusId;
    private Long educationId;
    private Long occupationId;
    private Long householdId;
    private Household householdByHouseholdId;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dob")
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Basic
    @Column(name = "dob_estimated")
    public Boolean getDobEstimated() {
        return dobEstimated;
    }

    public void setDobEstimated(Boolean dobEstimated) {
        this.dobEstimated = dobEstimated;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "gender_id")
    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    @Basic
    @Column(name = "marital_status_id")
    public Long getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(Long maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    @Basic
    @Column(name = "education_id")
    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    @Basic
    @Column(name = "occupation_id")
    public Long getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(Long occupationId) {
        this.occupationId = occupationId;
    }

    @Basic
    @Column(name = "household_id")
    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdMember that = (HouseholdMember) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dob, that.dob) &&
                Objects.equals(dobEstimated, that.dobEstimated) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(genderId, that.genderId) &&
                Objects.equals(maritalStatusId, that.maritalStatusId) &&
                Objects.equals(educationId, that.educationId) &&
                Objects.equals(occupationId, that.occupationId) &&
                Objects.equals(householdId, that.householdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dob, dobEstimated, firstName, lastName, genderId, maritalStatusId, educationId, occupationId, householdId);
    }

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", nullable = false)
    public Household getHouseholdByHouseholdId() {
        return householdByHouseholdId;
    }

    public void setHouseholdByHouseholdId(Household householdByHouseholdId) {
        this.householdByHouseholdId = householdByHouseholdId;
    }
}
