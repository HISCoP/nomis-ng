package org.nomisng.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.OrganisationUnit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CboProjectLocationDTO {

    private Long id;

    @NotNull(message = "cboProjectId is mandatory")
    private Long cboProjectId;

    private int archived;

    @NotNull(message = "organisationUnitId is mandatory")
    private Long organisationUnitId;

    private String organisationUnitName;

}
