package org.nomisng.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class CboDonorImplementerOrganisationUnitDTO {

    private Long id;

    private Long cboId;

    private Long donorId;

    private Long implementerId;

    private List<Long> organisationUnitIds;
}
