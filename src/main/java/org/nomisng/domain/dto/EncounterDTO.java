package org.nomisng.domain.dto;

import lombok.Data;
import org.nomisng.domain.entity.FormData;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class EncounterDTO implements Serializable {

    private Long id;

    private Date dateEncounter;

    private String formCode;

    private Long serviceCode;

    private Long organisationalUnitId;

    public List<FormData> getFormDataById;
}
