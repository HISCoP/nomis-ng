package org.nomisng.domain.dto;

import lombok.Data;
import org.nomisng.domain.entity.FormData;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EncounterDTO {

    private Long id;

    @NotNull(message = "dateEncounter is mandatory")
    private LocalDateTime dateEncounter;

    @NotBlank(message = "formCode is mandatory")
    private String formCode;

    private Long householdMemberId;

    @NotNull(message = "householdMemberId is mandatory")
    private Long householdId;

    private List<FormData> formData;

    private String formName;

    private String firstName;

    private String lastName;

    private String otherNames;
}
