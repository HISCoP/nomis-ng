package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data

public class ApplicationUserCboProjectDTO {

    private Long id;

    @NotNull(message = "applicationUserId is mandatory")
    private Long applicationUserId;

    @NotEmpty(message = "cboProjectIds should not be empty")
    @NotNull(message = "cboProjectIds is mandatory")
    private List<Long> cboProjectIds;
}
