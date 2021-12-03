package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.FormData;
import org.nomisng.domain.mapper.FormDataMapper;
import org.nomisng.repository.FormDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.nomisng.util.Constants.ArchiveStatus.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormDataService {
    private final FormDataRepository formDataRepository;
    private final FormDataMapper formDataMapper;
    private final UserService userService;

    public FormData update(Long id, FormDataDTO formDataDTO) {
        formDataRepository.findByIdAndCboProjectIdAndArchived(id,
                userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id +""));
        FormData formData = formDataMapper.toFormData(formDataDTO);
        formData.setId(id);
        formData.setArchived(UN_ARCHIVED);
        formData.setCboProjectId(userService.getUserWithRoles().get().getCurrentCboProjectId());
        return formDataRepository.save(formData);
    }

    public FormData getFormData(Long id){
        FormData formData = formDataRepository.findByIdAndCboProjectIdAndArchived(id,
                userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id +""));
        return formData;
    }

    public List<FormData> getAllFormData() {
        return formDataRepository.findAllByArchivedAndCboProjectIdOrderByIdDesc(UN_ARCHIVED,
                userService.getUserWithRoles().get().getCurrentCboProjectId());
    }

    public void delete(Long id) {
        FormData formData = formDataRepository.findByIdAndCboProjectIdAndArchived(id,
                userService.getUserWithRoles().get().getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id +""));
        formData.setArchived(ARCHIVED);
        formDataRepository.save(formData);
    }
}
