package org.nomisng.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VisitDTO{

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
