package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.Permission;
import org.nomisng.domain.mapper.FormMapper;
import org.nomisng.repository.FormRepository;
import org.nomisng.repository.PermissionRepository;
import org.nomisng.util.AccessRight;
import org.nomisng.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final FormMapper formMapper;
    private final AccessRight accessRight;
    private final PermissionRepository permissionRepository;
    private static final String READ = "Read";
    private static final String WRITE = "Write";
    private static final String DELETE = "Delete";
    private static final String UNDERSCORE = "_";
    private final UserService userService;

    public List getAllForms() {
        List<Form> forms = formRepository.findAllByArchivedOrderByIdAsc(UN_ARCHIVED);
        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();
        return getForms(forms, permissions);
    }

    public Form save(FormDTO formDTO) {
        if(formDTO.getCode() == null || formDTO.getCode().isEmpty()){
            formDTO.setCode(UUID.randomUUID().toString());
        }
        Optional<Form> formOptional = formRepository.findByNameAndArchived(formDTO.getName(), Constants.ArchiveStatus.UN_ARCHIVED);
        formOptional.ifPresent(form -> {
            throw new RecordExistException(Form.class, "Name", form.getName());
        });
        Form form = formMapper.toForm(formDTO);
        form.setArchived(UN_ARCHIVED);

        String read = UNDERSCORE + READ;
        String write = UNDERSCORE + WRITE;
        String delete = UNDERSCORE + DELETE;

        List<Permission> permissions = new ArrayList<>();

        permissions.add(new Permission(formDTO.getCode() + read, formDTO.getName() +" Read", UN_ARCHIVED));
        permissions.add(new Permission(formDTO.getCode() + write, formDTO.getName() +" Write", UN_ARCHIVED));
        permissions.add(new Permission(formDTO.getCode() + delete, formDTO.getName() +" Delete", UN_ARCHIVED));
        permissionRepository.saveAll(permissions);

        form.setArchived(UN_ARCHIVED);
        return formRepository.save(form);
    }

    public FormDTO getForm(Long id) {
        Form form = this.formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id+""));

        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();
        accessRight.grantAccess(form.getCode(), FormService.class, permissions);

        FormDTO formDTO = formMapper.toFormDTO(form);
        return formDTO;
    }

    public FormDTO getFormByFormCode(String formCode) {
        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();

        accessRight.grantAccess(formCode, FormService.class, permissions);

        Form form = this.formRepository.findByCodeAndArchived(formCode, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "formCode", formCode+""));

        FormDTO formDTO = formMapper.toFormDTO(form);
        return formDTO;
    }

    public Form update(Long id, FormDTO formDTO) {
        formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id+""));
        formDTO.setId(id);
        if(StringUtils.isBlank(formDTO.getCode())) throw new EntityNotFoundException(Form.class, "Code", formDTO.getCode()+"");
        return formRepository.save(formMapper.toForm(formDTO));
    }

    public void delete(Long id) {
        Form form = formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id+""));

        form.setArchived(Constants.ArchiveStatus.UN_ARCHIVED);
        formRepository.save(form);
    }

    /*public void setPermission(){
        List<Long> permissions = new ArrayList<>();
        List<RolePermission> rolePermissions = new ArrayList<>();
        permissionRepository.findAll().forEach(permission -> {
            if(permission.getId() != 1){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permission.getId());
                rolePermission.setRoleId(2L);
                rolePermissions.add(rolePermission);
            }
        });
        rolePermissionRepository.saveAll(rolePermissions);
    }*/

    private List getForms(List<Form> forms, Set<String> permissions){
        List<FormDTO> formList = new ArrayList<>();
        forms.forEach(form -> {
            if(!accessRight.grantAccessForm(form.getCode(), permissions)){
                return;
            }
            final FormDTO formDTO = formMapper.toFormDTO(form);
            formList.add(formDTO);
        });
        return formList;
    }
}