package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;

@Mapper(componentModel = "spring")
public interface FormMapper {
    Form toForm(FormDTO formDTO);

    FormDTO toFormDTO(Form form);
}
