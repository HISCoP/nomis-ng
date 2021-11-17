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

    /*public FormData save(FormData formData) {
        Optional<FormData> formDataOptional = formDataRepository.findByIdAndArchived(formData.getId(), UN_ARCHIVED);
        if(formDataOptional.isPresent())throw new RecordExistException(FormData.class, "Id", formData.getId() +"");
        //Long organisationUnitId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        formData.setOrganisationalUnitId(organisationUnitId);

        return formDataRepository.save(formData);
    }*/

    public FormData update(Long id, FormDataDTO formDataDTO) {
        formDataRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id +""));
        //Long organisationUnitId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        FormData formData = formDataMapper.toFormData(formDataDTO);
        //formData.setOrganisationUnitId(organisationUnitId);
        formData.setId(id);
        formData.setArchived(UN_ARCHIVED);
        return formDataRepository.save(formData);
    }

    public FormData getFormData(Long id){
        //Long organisationUnitId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        FormData formData = formDataRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id +""));
        return formData;
    }

    public List<FormData> getAllFormData() {
        //Long organisationUnitId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return formDataRepository.findAllByArchivedOrderByIdDesc(UN_ARCHIVED);
    }

    public void delete(Long id) {
        FormData formData = formDataRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id +""));
        formData.setArchived(UN_ARCHIVED);
        formDataRepository.save(formData);
    }
}
