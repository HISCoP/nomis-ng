package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.CboProjectDTO;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.CboProjectLocation;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.service.CboProjectLocationService;
import org.nomisng.service.CboProjectService;
import org.nomisng.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/cbo-project-location")
@RequiredArgsConstructor
public class CboProjectLocationController {
    private final CboProjectLocationService cboProjectLocationService;

    @GetMapping
    public ResponseEntity<List<OrganisationUnit>> getOrganisationUnitByCboProjectId() {
        return ResponseEntity.ok(cboProjectLocationService.getOrganisationUnitByCboProjectId());
    }

    @GetMapping("/state")
    public ResponseEntity<List<OrganisationUnit>> getState() {
        return ResponseEntity.ok(cboProjectLocationService.getState());
    }
    @GetMapping("/state/{stateId}/lga")
    public ResponseEntity<List<OrganisationUnit>> getState(@PathVariable Long stateId) {
        return ResponseEntity.ok(cboProjectLocationService.getLga(stateId));
    }
}
