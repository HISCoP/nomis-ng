package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.ApplicationUserCboProjectDTO;
import org.nomisng.domain.entity.*;
import org.nomisng.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserCboProjectService {
    private final ApplicationUserCboProjectRepository applicationUserCboProjectRepository;


    public List<ApplicationUserCboProject> save(ApplicationUserCboProjectDTO applicationUserCboProjectDTO) {
        List<ApplicationUserCboProject> applicationUserCboProjects = applicationUserCboProjectRepository
                .findAllByApplicationUserIdAndCboProjectIdAndArchived(applicationUserCboProjectDTO.getApplicationUserId(),
                UN_ARCHIVED,
                applicationUserCboProjectDTO.getCboProjectIds());

        if(!applicationUserCboProjects.isEmpty()){
            throw new RecordExistException(ApplicationUserCboProject.class, "ApplicationUserCboProject",
                    ""+ applicationUserCboProjects);
        }

        applicationUserCboProjects.clear();

        applicationUserCboProjectDTO.getCboProjectIds().forEach(cboProjectId ->{
            final ApplicationUserCboProject applicationUserCboProject = new ApplicationUserCboProject();

            applicationUserCboProject.setApplicationUserId(applicationUserCboProjectDTO.getApplicationUserId());
            applicationUserCboProject.setCboProjectId(cboProjectId);
            applicationUserCboProject.setArchived(UN_ARCHIVED);
            applicationUserCboProjects.add(applicationUserCboProject);
        });
        return applicationUserCboProjectRepository.saveAll(applicationUserCboProjects);
    }
}