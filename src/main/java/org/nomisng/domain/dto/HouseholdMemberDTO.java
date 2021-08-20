package org.nomisng.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.domain.entity.Audit;
import org.nomisng.domain.entity.Household;
import org.nomisng.util.converter.LocalDateConverter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;


@Data
public class HouseholdMemberDTO {

    private Long id;

    /*@Convert(converter = LocalDateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private Boolean dobEstimated;

    private String firstName;

    private String lastName;

    private Long genderId;

    private Long maritalStatusId;

    private Long educationId;

    private Long occupationId;*/

    private Long householdId;

    private Integer householdMemberType;

    private Object details;
}
