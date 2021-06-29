package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.domain.mapper.OvcServiceMapper;
import org.nomisng.repository.OvcServiceRepository;
import org.nomisng.util.Constants;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OvcServiceService {
    private final OvcServiceRepository ovcServiceRepository;
    private final OvcServiceMapper ovcServiceMapper;

    public OvcService save(OvcServiceDTO ovcServiceDTO) {
        final OvcService ovcService = ovcServiceMapper.toOvcService(ovcServiceDTO);
        if(ovcService.getCode() == null) {
            ovcService.setCode(UUID.randomUUID().toString());
        }
        if(ovcService.getArchived() == null) {
            ovcService.setArchived(Constants.ArchiveStatus.UN_ARCHIVED);
        }
        return this.ovcServiceRepository.save(ovcService);
    }

    public List<OvcServiceDTO> getAllOvcServices(){
        return ovcServiceMapper.toOvcServiceDTOS(ovcServiceRepository
                .findAllByArchivedIsNotOrderByIdDesc(Constants.ArchiveStatus.UN_ARCHIVED));
    }

    /*public List<Form> getFormByOvcServiceId(Long ovcServiceId){
        Optional<OvcService> optionalOvcService = ovcServiceRepository.findById(ovcServiceId);
        if(!optionalOvcService.isPresent() || optionalOvcService.get().getArchived() == ARCHIVED) throw new EntityNotFoundException(OvcService.class, "OvcService Id", ovcServiceId + "");
        List<Form> forms = optionalOvcService.get().getFormByOvcServiceCode().stream()
                .filter(form ->form.getArchived()!= null && form.getArchived()== UN_ARCHIVED)
                .sorted(Comparator.comparing(Form::getId).reversed())
                .collect(Collectors.toList());
        return forms;
    }*/

    public Domain getDomainByOvcServiceId(Long ovcServiceId){
        OvcService ovcService = ovcServiceRepository.findByIdAndArchived(ovcServiceId, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OvcService.class,"id:",ovcServiceId+""));

        return ovcService.getDomainByDomainId();
    }

    public Integer delete(Long id) {
        OvcService ovcService = ovcServiceRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OvcService.class, "OvcService Id", id + ""));
        ovcService.setArchived(Constants.ArchiveStatus.ARCHIVED);
        ovcServiceRepository.save(ovcService);
        return ovcService.getArchived();
    }

    public OvcService update(Long id, OvcServiceDTO ovcServiceDTO) {
        ovcServiceRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OvcService.class, "OvcService Id", id + ""));
        if(ovcServiceDTO.getArchived() == null){
            ovcServiceDTO.setArchived(Constants.ArchiveStatus.UN_ARCHIVED);
        }
        final OvcService ovcService = ovcServiceMapper.toOvcService(ovcServiceDTO);
        ovcService.setId(id);
        return ovcServiceRepository.save(ovcService);
    }
}
