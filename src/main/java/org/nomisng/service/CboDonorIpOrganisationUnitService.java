package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.CboDonorIpOrganisationUnitDTO;
import org.nomisng.domain.entity.CboDonorIpOrganisationUnit;
import org.nomisng.domain.entity.Ip;
import org.nomisng.domain.mapper.CboDonorIpOrganisationUnitMapper;
import org.nomisng.repository.CboDonorIpOrganisationUnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboDonorIpOrganisationUnitService {
    private final CboDonorIpOrganisationUnitRepository cboDonorIpOrganisationUnitRepository;
    private final CboDonorIpOrganisationUnitMapper cboDonorIpOrganisationUnitMapper;

    public List getAllCboDonorIpOrganisationUnits() {
        return cboDonorIpOrganisationUnitMapper.toCboDonorIpOrganisationUnitDTOS(cboDonorIpOrganisationUnitRepository.findAll());
    }

    public CboDonorIpOrganisationUnit save(CboDonorIpOrganisationUnitDTO cboDonorIpOrganisationUnitDTO) {
        return cboDonorIpOrganisationUnitRepository.save(cboDonorIpOrganisationUnitMapper.toCboDonorIpOrganisationUnit(cboDonorIpOrganisationUnitDTO));
    }

    public CboDonorIpOrganisationUnitDTO getCboDonorIpOrganisationUnit(Long id) {
        CboDonorIpOrganisationUnit cboDonorIpOrganisationUnit  = cboDonorIpOrganisationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CboDonorIpOrganisationUnit.class, "Id", id+""));
       return cboDonorIpOrganisationUnitMapper.toCboDonorIpOrganisationUnitDTO(cboDonorIpOrganisationUnit);
    }


    public CboDonorIpOrganisationUnit update(Long id, CboDonorIpOrganisationUnitDTO cboDonorIpOrganisationUnitDTO) {
        CboDonorIpOrganisationUnit cboDonorIpOrganisationUnit = cboDonorIpOrganisationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CboDonorIpOrganisationUnit.class, "Id", id+""));
        cboDonorIpOrganisationUnitDTO.setId(cboDonorIpOrganisationUnit.getId());
        return cboDonorIpOrganisationUnitRepository.save(cboDonorIpOrganisationUnitMapper.toCboDonorIpOrganisationUnit(cboDonorIpOrganisationUnitDTO));
    }

    public Integer delete(Long id) {
        return null;
    }
}