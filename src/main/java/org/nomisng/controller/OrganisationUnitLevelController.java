package org.nomisng.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.dto.OrganisationUnitLevelDTO;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.service.OrganisationUnitLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/organisation-unit-levels")
@Slf4j
@RequiredArgsConstructor
public class OrganisationUnitLevelController {
    private final OrganisationUnitLevelService organisationUnitLevelService;

    @PostMapping
    public ResponseEntity<OrganisationUnitLevelDTO> save(@Valid @RequestBody OrganisationUnitLevelDTO organisationUnitLevelDTO) {
        return ResponseEntity.ok(organisationUnitLevelService.save(organisationUnitLevelDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<OrganisationUnitLevelDTO> update(@PathVariable Long id, @Valid @RequestBody OrganisationUnitLevelDTO organisationUnitLevelDTO){
        return ResponseEntity.ok(organisationUnitLevelService.update(id, organisationUnitLevelDTO));
    }

    @GetMapping
    public ResponseEntity<List<OrganisationUnitLevelDTO>> getAllOrganizationUnitLevel(@RequestParam(required = false, defaultValue = "2") Integer status) {
        return ResponseEntity.ok(organisationUnitLevelService.getAllOrganizationUnitLevel(status));
    }

    @GetMapping("{id}/organisation-units")
    public ResponseEntity<List<OrganisationUnit>> getAllOrganisationUnitsByOrganizationUnitLevel(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitLevelService.getAllOrganisationUnitsByOrganizationUnitLevel(id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrganisationUnitLevelDTO> getOrganizationUnitLevel(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitLevelService.getOrganizationUnitLevel(id));
    }

    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        organisationUnitLevelService.delete(id);
    }*/
}
