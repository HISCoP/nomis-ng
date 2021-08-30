package org.nomisng.domain.dto;

import lombok.Data;

@Data

public class OrganisationUnitDTO {

    private Long id;

    private String name;

    private String description;

    private Long organisationUnitLevelId;

    private Long parentOrganisationUnitId;

    private String  parentOrganisationUnitName;

    private String  parentParentOrganisationUnitName;

}
