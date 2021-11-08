package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.CboDonorImplementerOrganisationUnitDTO;
import org.nomisng.domain.entity.CboDonorImplementerOrganisationUnit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CboDonorImplementerOrganisationUnitMapper {
    CboDonorImplementerOrganisationUnit toCboDonorImplementerOrganisationUnit(CboDonorImplementerOrganisationUnitDTO cboDonorImplementerOrganisationUnitDTO);

    CboDonorImplementerOrganisationUnitDTO toCboDonorImplementerOrganisationUnitDTO(CboDonorImplementerOrganisationUnit cboDonorImplementerOrganisationUnit);

    List<CboDonorImplementerOrganisationUnitDTO> toCboDonorImplementerOrganisationUnitDTOS(List<CboDonorImplementerOrganisationUnit> cboDonorImplementerOrganisationUnits);
}
