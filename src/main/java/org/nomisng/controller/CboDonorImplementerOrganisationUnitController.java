package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.CboDonorImplementerOrganisationUnitDTO;
import org.nomisng.domain.entity.CboDonorImplementerOrganisationUnit;
import org.nomisng.service.CboDonorImplementerOrganisationUnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cbo-donor-ip-organisation-units")
@RequiredArgsConstructor
public class CboDonorImplementerOrganisationUnitController {
    private final CboDonorImplementerOrganisationUnitService cboDonorImplementerOrganisationUnitService;

    @GetMapping
    public ResponseEntity<List<CboDonorImplementerOrganisationUnitDTO>> getAllCboDonorIpOrganisationUnits() {
        return ResponseEntity.ok(cboDonorImplementerOrganisationUnitService.getAllCboDonorIpOrganisationUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CboDonorImplementerOrganisationUnitDTO> getCbo(@PathVariable Long id) {
        return ResponseEntity.ok(cboDonorImplementerOrganisationUnitService.getCboDonorIpOrganisationUnit(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<CboDonorImplementerOrganisationUnit>> save(@RequestBody CboDonorImplementerOrganisationUnitDTO cboDonorImplementerOrganisationUnitDTO) {
        return ResponseEntity.ok(cboDonorImplementerOrganisationUnitService.save(cboDonorImplementerOrganisationUnitDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CboDonorImplementerOrganisationUnit> updateCbo(@RequestBody CboDonorImplementerOrganisationUnitDTO cboDonorImplementerOrganisationUnitDTO, @PathVariable Long id) {
        return ResponseEntity.ok(cboDonorImplementerOrganisationUnitService.update(id, cboDonorImplementerOrganisationUnitDTO));
    }
}
