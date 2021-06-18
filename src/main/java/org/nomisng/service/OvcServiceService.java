package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.domain.mapper.OvcServiceMapper;
import org.nomisng.repository.OvcServiceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OvcServiceService {
    public static final int ARCHIVED = 1;
    private final OvcServiceRepository ovcServiceRepository;
    private final OvcServiceMapper ovcServiceMapper;
    private static final int UN_ARCHIVED = 0;

    public OvcService save(OvcServiceDTO ovcServiceDTO) {

        final OvcService ovcService = ovcServiceMapper.toOvcService(ovcServiceDTO);
        if(ovcService.getCode() == null) {
            ovcService.setCode(UUID.randomUUID().toString());
        }
        if(ovcService.getArchived() == null) {
            ovcService.setArchived(UN_ARCHIVED);
        }
        return this.ovcServiceRepository.save(ovcService);
    }

    public List<OvcServiceDTO> getAllOvcServices(){
        return this.ovcServiceMapper.toOvcServiceDTOS(ovcServiceRepository.findAllByArchivedIsNotOrderByIdDesc(ARCHIVED));
    }

    public List<Form> getFormByOvcServiceId(Long ovcServiceId){
        Optional<OvcService> optionalOvcService = ovcServiceRepository.findById(ovcServiceId);
        if(!optionalOvcService.isPresent() || optionalOvcService.get().getArchived() == ARCHIVED) throw new EntityNotFoundException(OvcService.class, "OvcService Id", ovcServiceId + "");
        List<Form> forms = optionalOvcService.get().getFormByOvcServiceCode().stream()
                .filter(form ->form.getArchived()!= null && form.getArchived()== UN_ARCHIVED)
                .sorted(Comparator.comparing(Form::getId).reversed())
                .collect(Collectors.toList());
        return forms;
    }

    public Domain getDomainByOvcServiceId(Long ovcServiceId){
        OvcService ovcService = ovcServiceRepository.findByIdAndArchived(ovcServiceId, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OvcService.class,"id:",ovcServiceId+""));

        return ovcService.getDomainByDomainId();
    }

    public Integer delete(Long id) {
        OvcService ovcService = ovcServiceRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(
                () -> new EntityNotFoundException(OvcService.class, "OvcService Id", id + ""));
        ovcService.setArchived(ARCHIVED);
        ovcServiceRepository.save(ovcService);
        return ovcService.getArchived();
    }

    public OvcService update(Long id, OvcServiceDTO ovcServiceDTO) {
        Optional<OvcService> programOptional = ovcServiceRepository.findById(id);
        if(!programOptional.isPresent())throw new EntityNotFoundException(OvcService.class, "Id", id +"");
        if(ovcServiceDTO.getArchived() == null){
            ovcServiceDTO.setArchived(UN_ARCHIVED);
        }
        final OvcService ovcService = this.ovcServiceMapper.toOvcService(ovcServiceDTO);
        ovcService.setId(id);
        return ovcServiceRepository.save(ovcService);
    }
}
