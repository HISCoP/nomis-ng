package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.ImplementerDTO;
import org.nomisng.domain.entity.Donor;
import org.nomisng.domain.entity.Implementer;
import org.nomisng.domain.mapper.ImplementerMapper;
import org.nomisng.repository.ImplementerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImplementerService {
    private final ImplementerRepository implementerRepository;
    private final ImplementerMapper implementerMapper;

    public List getAllImplementers() {
        return implementerMapper.toImplementerDTOS(implementerRepository.findAll());
    }

    public Implementer save(ImplementerDTO implementerDTO) {
        implementerRepository.findByNameAndArchived(implementerDTO.getName(), UN_ARCHIVED).ifPresent(implementer -> {
            throw new RecordExistException(Implementer.class, "Name", ""+implementerDTO.getName());
        });
        //Temporary, will be replace with implementer code
        if(implementerDTO.getCode() == null){
            implementerDTO.setCode(UUID.randomUUID().toString());
        }
        Implementer implementer = implementerMapper.toImplementer(implementerDTO);
        implementer.setArchived(UN_ARCHIVED);
        return implementerRepository.save(implementer);}

    public ImplementerDTO getImplementer(Long id) {
        Implementer implementer = implementerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Implementer.class, "Id", id+""));
       return implementerMapper.toImplementerDTO(implementer);
    }


    public Implementer update(Long id, ImplementerDTO implementerDTO) {
        Implementer implementer = implementerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Implementer.class, "Id", id+""));
        implementerDTO.setId(implementer.getId());
        return implementerRepository.save(implementerMapper.toImplementer(implementerDTO));
    }

    public void delete(Long id) {

    }
}