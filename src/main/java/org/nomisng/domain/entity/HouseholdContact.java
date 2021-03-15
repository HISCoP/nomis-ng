package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "household_contact", schema = "public", catalog = "nomis")
public class HouseholdContact {
    private Long id;
    private String mobilePhoneNumber;
    private String alternatePhoneNumber;
    private String email;
    private String zipCode;
    private String city;
    private String street;
    private String landmark;
    private Long countryId;
    private Long stateId;
    private Long provinceId;
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
    @Column(name = "mobile_phone_number")
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    @Basic
    @Column(name = "alternate_phone_number")
    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "landmark")
    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @Basic
    @Column(name = "country_id")
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Basic
    @Column(name = "state_id")
    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "province_id")
    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
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
        HouseholdContact that = (HouseholdContact) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(mobilePhoneNumber, that.mobilePhoneNumber) &&
                Objects.equals(alternatePhoneNumber, that.alternatePhoneNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(landmark, that.landmark) &&
                Objects.equals(countryId, that.countryId) &&
                Objects.equals(stateId, that.stateId) &&
                Objects.equals(provinceId, that.provinceId) &&
                Objects.equals(householdId, that.householdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mobilePhoneNumber, alternatePhoneNumber, email, zipCode, city, street, landmark, countryId, stateId, provinceId, householdId);
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
