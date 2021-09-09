package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.domain.entity.Donor;
import org.nomisng.domain.mapper.DonorMapper;
import org.nomisng.repository.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DonorService {
    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;

    public List getAllDonors() {
        return donorMapper.toDonorDTOS(donorRepository.findAll());
    }

    public Donor save(DonorDTO donorDTO) {
        return donorRepository.save(donorMapper.toDonor(donorDTO));
    }

    public DonorDTO getDonor(Long id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Donor.class, "Id", id+""));
       return donorMapper.toDonorDTO(donor);
    }


    public Donor update(Long id, DonorDTO donorDTO) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Cbo.class, "Id", id+""));
        donorDTO.setId(donor.getId());
        return donorRepository.save(donorMapper.toDonor(donorDTO));
    }

    public void delete(Long id) {

    }
}