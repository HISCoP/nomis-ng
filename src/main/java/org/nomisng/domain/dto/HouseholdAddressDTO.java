package org.nomisng.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.nomisng.domain.entity.Audit;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.OrganisationUnit;

import javax.persistence.*;

@Data
public class HouseholdAddressDTO {

    private Long id;

    private String zipCode;

    private String city;

    private String street;

    private String landmark;

    private Long countryId;

    private Long stateId;

    private Long provinceId;

    private Long householdId;
}
