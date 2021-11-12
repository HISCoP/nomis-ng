package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.CboProjectDTO;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.OrganisationUnit;
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

import java.util.List;

@RestController
@RequestMapping("api/cbo-project")
@RequiredArgsConstructor
public class CboProjectController {
    private final CboProjectService cboProjectService;

    /*@GetMapping
    public ResponseEntity<List<CboDonorImplementerOrganisationUnitDTO>> getAllCboDonorIpOrganisationUnits() {
        return ResponseEntity.ok(cboDonorImplementerOrganisationUnitService.getAllCboDonorIpOrganisationUnits());
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<CboProjectDTO> getCboProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(cboProjectService.getCboProjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<CboProjectDTO>> getCboProject(@RequestParam(required = false, defaultValue = "0") Long cboId,
                                                               @RequestParam(required = false, defaultValue = "0") Long donorId,
                                                               @RequestParam(required = false, defaultValue = "0") Long implementerId,
                                                               @PageableDefault(value = 100) Pageable pageable) {

        Page<CboProject> page = cboProjectService.getCboProject(cboId, donorId, implementerId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(cboProjectService.getCboProjectsFromPage(page), headers, HttpStatus.OK);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<CboProjectDTO> save(@RequestBody CboProjectDTO cboProjectDTO) {
        return ResponseEntity.ok(cboProjectService.save(cboProjectDTO));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<CboProject> update(@RequestBody CboProjectDTO cboProjectDTO, @PathVariable Long id) {
        return ResponseEntity.ok(cboProjectService.update(id, cboProjectDTO));
    }

    /*@GetMapping("/organisation-units")
    public ResponseEntity<List<OrganisationUnit>> getOrganisationUnitByCboProjectId() {
        return ResponseEntity.ok(cboProjectService.getOrganisationUnitByCboProjectId());
    }*/
}
