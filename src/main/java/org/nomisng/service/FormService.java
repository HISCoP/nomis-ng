package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.mapper.FormMapper;
import org.nomisng.repository.FormRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@org.springframework.stereotype.Service
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
        Optional<Form> formOptional = formRepository.findByNameAndServiceIdAndArchived(formDTO.getName(), formDTO.getServiceId(), UN_ARCHIVED);
        if (formOptional.isPresent()) {
            throw new RecordExistException(Form.class, "Name", formDTO.getName());
        }
        Form form = formMapper.toFormDTO(formDTO);
        form.setArchived(UN_ARCHIVED);

        return formRepository.save(form);
    }

    public Form getForm(Long id) {
        Optional<Form> formOptional = this.formRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!formOptional.isPresent()) throw new EntityNotFoundException(Form.class, "Id", id+"");

        return formOptional.get();
    }

    public Form getFormsByFormCode(String formCode) {
        Optional<Form> formOptional = formRepository.findByCodeAndArchived(formCode, UN_ARCHIVED);
        if(!formOptional.isPresent()) {
            throw new EntityNotFoundException(Form.class, "Form Code", formCode);
        }
        return formOptional.get();
    }

    public Form update(Long id, FormDTO formDTO) {
        Optional<Form> formOptional = formRepository.findByIdAndArchived(id, UN_ARCHIVED);
        log.info("form optional  is" + formOptional.get());
        if(!formOptional.isPresent())throw new EntityNotFoundException(Form.class, "Id", id +"");

        Form form = formMapper.toFormDTO(formDTO);
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