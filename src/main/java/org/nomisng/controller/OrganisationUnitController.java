package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.service.OrganisationUnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organisation-units")
@Slf4j
@RequiredArgsConstructor
public class OrganisationUnitController {

    private final OrganisationUnitService organisationUnitService;

    @PostMapping
    public ResponseEntity<OrganisationUnit> save(@RequestBody OrganisationUnitDTO organisationUnitDTO) {
        return ResponseEntity.ok(organisationUnitService.save(organisationUnitDTO));
    }

    /*@PostMapping("/save-all")
    public ResponseEntity<List<OrganisationUnit>> saveAll(@RequestParam String path) {
        return ResponseEntity.ok(organisationUnitService.saveAll(path));
    }*/

    @PutMapping("{id}")
    public ResponseEntity<OrganisationUnit> update(@PathVariable Long id, @RequestBody OrganisationUnitDTO organisationUnitDTO) {
        return ResponseEntity.ok(organisationUnitService.update(id, organisationUnitDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationUnit> getOrganizationUnit(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitService.getOrganizationUnit(id));
    }

    @GetMapping
    public ResponseEntity<List<OrganisationUnit>> getAllOrganizationUnit() {
        return ResponseEntity.ok(organisationUnitService.getAllOrganizationUnit());
    }

    @GetMapping ("/parent-org-unit/{id}")
    public  ResponseEntity<List<OrganisationUnit>>  getOrganisationUnitByParentOrganisationUnitId(@PathVariable Long id) {
        return ResponseEntity.ok(this.organisationUnitService.getOrganisationUnitByParentOrganisationUnitId(id));
    }

    @GetMapping ("/{parentOrgUnitId}/{orgUnitLevelId}")
    public  ResponseEntity<List<OrganisationUnit>>  getOrganisationUnitByParentOrganisationUnitIdAndOrganisationUnitLevelId(
            @PathVariable Long parentOrgUnitId, @PathVariable Long orgUnitLevelId) {
        return ResponseEntity.ok(this.organisationUnitService.getOrganisationUnitByParentOrganisationUnitIdAndOrganisationUnitLevelId(parentOrgUnitId, orgUnitLevelId));
    }

    @GetMapping ("/organisation-unit-level/{id}")
    public  ResponseEntity<List<OrganisationUnit>>  getOrganisationUnitByOrganisationUnitLevelId(@PathVariable Long id) {
        return ResponseEntity.ok(this.organisationUnitService.getOrganisationUnitByOrganisationUnitLevelId(id));
    }

    @GetMapping ("/organisation-unit-levels/{id}")
    public  ResponseEntity<List<OrganisationUnit>>  getAllOrganisationUnitByOrganisationUnitLevelId(@PathVariable Long id) {
        return ResponseEntity.ok(this.organisationUnitService.getAllOrganisationUnitByOrganisationUnitLevelId(id));
    }

    @GetMapping ("/hierarchy/{parentOrgUnitId}/{orgUnitLevelId}")
    public  ResponseEntity<List<OrganisationUnitDTO>>  getOrganisationUnitSubsetByParentOrganisationUnitIdAndOrganisationUnitLevelId(
            @PathVariable Long parentOrgUnitId, @PathVariable Long orgUnitLevelId) {
        return ResponseEntity.ok(this.organisationUnitService.getOrganisationUnitSubsetByParentOrganisationUnitIdAndOrganisationUnitLevelId(parentOrgUnitId, orgUnitLevelId));
    }

    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitService.delete(id));
    }*/
}
