package org.nomisng.domain.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class CboProjectDTO {

    private Long id;

    private Long cboId;

    private Long donorId;

    private Long implementerId;

    private List<Long> organisationUnitIds;

    private String cboName;

    private String donorName;

    private String implementerName;

    private String organisationUnitName;

    private String description;

    private List organisationUnits;
}
