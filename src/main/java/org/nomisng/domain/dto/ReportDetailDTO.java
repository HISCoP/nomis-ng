package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
public class ReportDetailDTO {
    @NotNull(message = "reportId is mandatory")
    private Long reportId;

    private String reportName;

    @NotNull(message = "reportFormat is mandatory")
    private String reportFormat;
    Map<String, Object> parameters;
}
