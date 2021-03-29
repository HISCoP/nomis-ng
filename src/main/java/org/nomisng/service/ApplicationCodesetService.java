package org.nomisng.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.ApplicationCodesetDTO;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.domain.mapper.ApplicationCodesetMapper;
import org.nomisng.repository.ApplicationCodesetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ApplicationCodesetService {

    private final ApplicationCodesetRepository applicationCodesetRepository;
    private final ApplicationCodesetMapper applicationCodesetMapper;
    private static final int ARCHIVED = 1;
    private static final int UN_ARCHIVED = 0;

    public List<ApplicationCodesetDTO> getAllApplicationCodeset(){
        List<ApplicationCodeset> applicationCodesets = applicationCodesetRepository.findAllByArchivedOrderByIdAsc(UN_ARCHIVED);

        return applicationCodesetMapper.toApplicationCodesetDTOList(applicationCodesets);
    }

    public ApplicationCodeset save(ApplicationCodesetDTO applicationCodesetDTO){
        Optional<ApplicationCodeset> applicationCodesetOptional = applicationCodesetRepository.findByDisplayAndCodesetGroupAndArchived(applicationCodesetDTO.getDisplay(),
                applicationCodesetDTO.getCodesetGroup(), UN_ARCHIVED);
        if (applicationCodesetOptional.isPresent()) {
            throw new RecordExistException(ApplicationCodeset.class,"Display:",applicationCodesetDTO.getDisplay());
        }

        final ApplicationCodeset applicationCodeset = applicationCodesetMapper.toApplicationCodeset(applicationCodesetDTO);
        applicationCodeset.setCode(UUID.randomUUID().toString());
        applicationCodeset.setArchived(UN_ARCHIVED);

        return applicationCodesetRepository.save(applicationCodeset);
    }

    public List<ApplicationCodesetDTO> getApplicationCodeByCodesetGroup(String codesetGroup){
        List<ApplicationCodeset> applicationCodesetList = applicationCodesetRepository.findAllByCodesetGroupAndArchivedOrderByIdAsc(codesetGroup, UN_ARCHIVED);

        return applicationCodesetMapper.toApplicationCodesetDTOList(applicationCodesetList);
    }

    public ApplicationCodesetDTO getApplicationCodeset(Long id){
        Optional<ApplicationCodeset> applicationCodeset = applicationCodesetRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!applicationCodeset.isPresent()) throw new EntityNotFoundException(ApplicationCodeset.class,"Display:",id+"");

        return  applicationCodesetMapper.toApplicationCodesetDTO(applicationCodeset.get());
    }

    public ApplicationCodeset update(Long id, ApplicationCodesetDTO applicationCodesetDTO){
        Optional<ApplicationCodeset> applicationCodesetOptional = applicationCodesetRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!applicationCodesetOptional.isPresent()) {
            throw new EntityNotFoundException(ApplicationCodeset.class,"Display:",id+"");
        }
        final ApplicationCodeset applicationCodeset = applicationCodesetMapper.toApplicationCodeset(applicationCodesetDTO);
        applicationCodeset.setId(id);
        applicationCodeset.setArchived(UN_ARCHIVED);
        return applicationCodesetRepository.save(applicationCodeset);
    }

    public Integer delete(Long id){
        Optional<ApplicationCodeset> applicationCodesetOptional = applicationCodesetRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!applicationCodesetOptional.isPresent()) throw new EntityNotFoundException(ApplicationCodeset.class,"Display:",id+"");
        ApplicationCodeset applicationCodeset = applicationCodesetOptional.get();
        applicationCodeset.setArchived(ARCHIVED);

        applicationCodesetRepository.save(applicationCodeset);

        return applicationCodeset.getArchived();
    }

    public Boolean exist(String display, String codesetGroup){
        return applicationCodesetRepository.existsByDisplayAndCodesetGroup(display, codesetGroup);
    }
}
