package org.nomisng.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.nomisng.domain.entity.Audit;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.OrganisationUnit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
public class HouseholdAddressDTO {

    private Long id;

    private String zipCode;

    private String city;

    private String street;

    private String landmark;

    @NotNull(message = "countryId is mandatory")
    private Long countryId;

    @NotNull(message = "stateId is mandatory")
    private Long stateId;

    private Long provinceId;

    @NotNull(message = "householdId is mandatory")
    private Long householdId;

    private Integer active;
}
