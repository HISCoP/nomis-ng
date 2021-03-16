package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;

@Mapper(componentModel = "spring")
public interface FormMapper {
    FormDTO toForm(Form form);
    Form toFormDTO(FormDTO formDTO);
}
