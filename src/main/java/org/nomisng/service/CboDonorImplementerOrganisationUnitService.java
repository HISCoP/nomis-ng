package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.CboDonorImplementerOrganisationUnitDTO;
import org.nomisng.domain.entity.CboDonorImplementerOrganisationUnit;
import org.nomisng.domain.mapper.CboDonorImplementerOrganisationUnitMapper;
import org.nomisng.repository.CboDonorImplementerOrganisationUnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboDonorImplementerOrganisationUnitService {
    private final CboDonorImplementerOrganisationUnitRepository cboDonorImplementerOrganisationUnitRepository;
    private final CboDonorImplementerOrganisationUnitMapper cboDonorImplementerOrganisationUnitMapper;

    public List getAllCboDonorIpOrganisationUnits() {
        return cboDonorImplementerOrganisationUnitMapper.toCboDonorImplementerOrganisationUnitDTOS(cboDonorImplementerOrganisationUnitRepository.findAll());
    }

    public List<CboDonorImplementerOrganisationUnit> save(CboDonorImplementerOrganisationUnitDTO cboDonorImplementerOrganisationUnitDTO) {
        List <CboDonorImplementerOrganisationUnit> cboDonorImplementerOrganisationUnits = new ArrayList<>();
        cboDonorImplementerOrganisationUnitDTO.getOrganisationUnitIds().forEach(organisationUnitId ->{
            CboDonorImplementerOrganisationUnit cboDonorImplementerOrganisationUnit = cboDonorImplementerOrganisationUnitMapper.toCboDonorImplementerOrganisationUnit(cboDonorImplementerOrganisationUnitDTO);
            cboDonorImplementerOrganisationUnit.setOrganisationUnitId(organisationUnitId);
            cboDonorImplementerOrganisationUnits.add(cboDonorImplementerOrganisationUnit);
        });
        return cboDonorImplementerOrganisationUnitRepository.saveAll(cboDonorImplementerOrganisationUnits);
    }

    public CboDonorImplementerOrganisationUnitDTO getCboDonorIpOrganisationUnit(Long id) {
        CboDonorImplementerOrganisationUnit cboDonorImplementerOrganisationUnit = cboDonorImplementerOrganisationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CboDonorImplementerOrganisationUnit.class, "Id", id+""));
       return cboDonorImplementerOrganisationUnitMapper.toCboDonorImplementerOrganisationUnitDTO(cboDonorImplementerOrganisationUnit);
    }


    public CboDonorImplementerOrganisationUnit update(Long id, CboDonorImplementerOrganisationUnitDTO cboDonorImplementerOrganisationUnitDTO) {
        CboDonorImplementerOrganisationUnit cboDonorImplementerOrganisationUnit = cboDonorImplementerOrganisationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CboDonorImplementerOrganisationUnit.class, "Id", id+""));
        cboDonorImplementerOrganisationUnitDTO.setId(cboDonorImplementerOrganisationUnit.getId());
        return cboDonorImplementerOrganisationUnitRepository.save(cboDonorImplementerOrganisationUnitMapper.toCboDonorImplementerOrganisationUnit(cboDonorImplementerOrganisationUnitDTO));
    }

    public Integer delete(Long id) {
        return null;
    }
}