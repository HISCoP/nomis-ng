package org.nomisng.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.domain.entity.Audit;
import org.nomisng.domain.entity.Flag;
import org.nomisng.domain.entity.Household;
import org.nomisng.util.converter.LocalDateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Data
public class HouseholdMemberDTO {

    private Long id;

    @NotNull(message = "householdId is mandatory")
    private Long householdId;

    @NotNull(message = "householdMemberType is mandatory")
    private Integer householdMemberType;

    private String uniqueId;

    private Object details;

    private int archived;

    private List<Flag> flags;
}
