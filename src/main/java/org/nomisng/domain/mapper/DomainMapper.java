package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.entity.Domain;

@Mapper(componentModel = "spring")
public interface DomainMapper {
    Domain toDomain(DomainDTO domainDTO);

    DomainDTO toDomainDTO(Domain domain);
}
