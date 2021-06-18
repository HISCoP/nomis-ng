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

    @NotNull(message = "ovcServiceCode cannot be null")
    private Long ovcServiceCode;

    public List<FormData> formData;

    private Object data;

}
