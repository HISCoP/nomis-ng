package org.nomisng.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.domain.entity.Donor;
import org.nomisng.domain.entity.Ip;
import org.nomisng.domain.entity.OrganisationUnit;

import javax.persistence.*;

@Data
public class CboDonorIpOrganisationUnitDTO {

    private Long id;

    private Long cboId;

    private Long donorId;

    private Long ipId;

    private Long organisationUnitId;
}
