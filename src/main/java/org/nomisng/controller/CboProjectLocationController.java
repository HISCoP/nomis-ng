package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.CboProjectDTO;
import org.nomisng.domain.dto.CboProjectLocationDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cbo-project-location")
@RequiredArgsConstructor
public class CboProjectLocationController {
    private final CboProjectLocationService cboProjectLocationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<List<OrganisationUnit>> getOrganisationUnitByCboProjectId() {
        return ResponseEntity.ok(cboProjectLocationService.getOrganisationUnitByCboProjectId());
    }

    @GetMapping("/state")
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<List<OrganisationUnit>> getState() {
        return ResponseEntity.ok(cboProjectLocationService.getState());
    }
    @GetMapping("/state/{stateId}/lga")
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<List<OrganisationUnit>> getState(@PathVariable Long stateId) {
        return ResponseEntity.ok(cboProjectLocationService.getLga(stateId));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<List<CboProjectLocation>> update(@Valid @RequestBody List<CboProjectLocationDTO> cboProjectLocationDTOS) {
        return ResponseEntity.ok(cboProjectLocationService.update(cboProjectLocationDTOS));
    }
}
