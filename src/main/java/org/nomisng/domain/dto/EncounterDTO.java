package org.nomisng.domain.dto;

import lombok.Data;
import org.nomisng.domain.entity.FormData;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EncounterDTO implements Serializable {

    private Long id;

    private LocalDateTime dateEncounter;

    @NotNull(message = "formCode cannot be null")
    private String formCode;

    private Long householdMemberId;

    private Long householdId;

    private List<FormData> formData;

    private String formName;

    private String firstName;

    private String lastName;

    private String otherNames;
}
