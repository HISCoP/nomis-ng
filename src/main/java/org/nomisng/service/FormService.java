package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.mapper.FormMapper;
import org.nomisng.repository.FormRepository;
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
    private static final int UN_ARCHIVED = 0;

    public List getAllForms() {
        return formRepository.findAllByArchivedOrderByIdAsc(UN_ARCHIVED);
    }

    public Form save(FormDTO formDTO) {
        if(formDTO.getCode() == null || formDTO.getCode().isEmpty()){
            formDTO.setCode(UUID.randomUUID().toString());
        }
        Optional<Form> formOptional = formRepository.findByNameAndAndArchived(formDTO.getName(), UN_ARCHIVED);
        if (formOptional.isPresent()) {
            throw new RecordExistException(Form.class, "Name", formDTO.getName());
        }
        Form form = formMapper.toForm(formDTO);
        form.setArchived(UN_ARCHIVED);

        return formRepository.save(form);
    }

    public FormDTO getForm(Long id) {
        Optional<Form> formOptional = this.formRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!formOptional.isPresent()) throw new EntityNotFoundException(Form.class, "Id", id+"");

        Form form = formOptional.get();
        FormDTO formDTO = formMapper.toFormDTO(form);
        return formDTO;
    }

    public FormDTO getFormByFormCode(String formCode) {
        Optional<Form> formOptional = formRepository.findByCodeAndArchived(formCode, UN_ARCHIVED);
        if(!formOptional.isPresent()) {
            throw new EntityNotFoundException(Form.class, "Form Code", formCode);
        }
        Form form = formOptional.get();
        FormDTO formDTO = formMapper.toFormDTO(form);
        return formDTO;
    }

    public Form update(Long id, FormDTO formDTO) {
        Optional<Form> formOptional = formRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!formOptional.isPresent())throw new EntityNotFoundException(Form.class, "Id", id +"");

        Form form = formMapper.toForm(formDTO);
        form.setId(id);
        return formRepository.save(form);
    }

    public Integer delete(Long id) {
        Optional<Form> formOptional = formRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!formOptional.isPresent())throw new EntityNotFoundException(Form.class, "Id", id +"");

        formOptional.get().setArchived(ARCHIVED);
        return formOptional.get().getArchived();
    }
}