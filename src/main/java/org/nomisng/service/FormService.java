package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.mapper.FormMapper;
import org.nomisng.repository.FormRepository;
import org.nomisng.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final FormMapper formMapper;
    private static final int ARCHIVED = 1;

    public List getAllForms() {
        return formRepository.findAllByArchivedOrderByIdAsc(Constants.ArchiveStatus.UN_ARCHIVED);
    }

    public Form save(FormDTO formDTO) {
        if(formDTO.getCode() == null || formDTO.getCode().isEmpty()){
            formDTO.setCode(UUID.randomUUID().toString());
        }
        Optional<Form> formOptional = formRepository.findByNameAndAndArchived(formDTO.getName(), Constants.ArchiveStatus.UN_ARCHIVED);
        formOptional.ifPresent(form -> {
            throw new RecordExistException(Form.class, "Name", form.getName());
        });
        Form form = formMapper.toForm(formDTO);
        form.setArchived(Constants.ArchiveStatus.UN_ARCHIVED);

        return formRepository.save(form);
    }

    public FormDTO getForm(Long id) {
        Form form = this.formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id+""));

        FormDTO formDTO = formMapper.toFormDTO(form);
        return formDTO;
    }

    public FormDTO getFormByFormCode(String formCode) {
        Form form = this.formRepository.findByCodeAndArchived(formCode, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "formCode", formCode+""));

        FormDTO formDTO = formMapper.toFormDTO(form);
        return formDTO;
    }

    public Form update(Long id, FormDTO formDTO) {
        formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id+""));
        formDTO.setId(id);
        return formRepository.save(formMapper.toForm(formDTO));
    }

    public void delete(Long id) {
        Form form = formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id+""));

        form.setArchived(ARCHIVED);
        formRepository.save(form);
    }
}