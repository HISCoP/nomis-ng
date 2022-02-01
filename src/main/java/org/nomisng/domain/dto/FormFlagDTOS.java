package org.nomisng.domain.dto;

import lombok.Data;
import org.nomisng.domain.entity.Flag;

import java.util.List;

@Data
public class FormFlagDTOS {

    private List<FormFlagDTO> formFlagDTOS;

    private Flag flag;
}
