package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.CboDonorIpOrganisationUnitDTO;
import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.CboDonorIpOrganisationUnit;
import org.nomisng.domain.entity.Donor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CboDonorIpOrganisationUnitMapper {
    CboDonorIpOrganisationUnit toCboDonorIpOrganisationUnit(CboDonorIpOrganisationUnitDTO cboDonorIpOrganisationUnitDTO);

    CboDonorIpOrganisationUnitDTO toCboDonorIpOrganisationUnitDTO(CboDonorIpOrganisationUnit cboDonorIpOrganisationUnit);

    List<CboDonorIpOrganisationUnitDTO> toCboDonorIpOrganisationUnitDTOS(List<CboDonorIpOrganisationUnit> cboDonorIpOrganisationUnits);
}
