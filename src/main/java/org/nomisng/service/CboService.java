package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.CboDTO;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.domain.mapper.CboMapper;
import org.nomisng.repository.CboRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboService {
    private final CboRepository cboRepository;
    private final CboMapper cboMapper;

    public List getAllCbos() {
        return cboMapper.toCboDTOS(cboRepository.findAll());
    }

    public Cbo save(CboDTO cboDTO) {
        return cboRepository.save(cboMapper.toCbo(cboDTO));
    }

    public CboDTO getCbo(Long id) {
        Cbo cbo = cboRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Cbo.class, "Id", id+""));
       return cboMapper.toCboDTO(cbo);
    }


    public Cbo update(Long id, CboDTO cboDTO) {
        Cbo cbo = cboRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Cbo.class, "Id", id+""));
        cboDTO.setId(cbo.getId());
        return cboRepository.save(cboMapper.toCbo(cboDTO));
    }

    public Integer delete(Long id) {
        return null;
    }
}