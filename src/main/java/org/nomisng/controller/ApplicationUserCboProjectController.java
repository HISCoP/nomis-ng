package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.ApplicationUserCboProjectDTO;
import org.nomisng.domain.entity.ApplicationUserCboProject;
import org.nomisng.domain.entity.CboProjectLocation;
import org.nomisng.service.ApplicationUserCboProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/application-user-cbo-project")
@RequiredArgsConstructor
public class ApplicationUserCboProjectController {
    private final ApplicationUserCboProjectService applicationUserCboProjectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ApplicationUserCboProject>> save(@RequestBody @Valid ApplicationUserCboProjectDTO applicationUserCboProjectDTO) {
        return ResponseEntity.ok(applicationUserCboProjectService.save(applicationUserCboProjectDTO));
    }
}
