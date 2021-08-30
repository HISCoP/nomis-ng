package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.CboDonorIpOrganisationUnitDTO;
import org.nomisng.domain.entity.CboDonorIpOrganisationUnit;
import org.nomisng.service.CboDonorIpOrganisationUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cbo-donor-ip-organisation-units")
@RequiredArgsConstructor
public class CboDonorIpOrganisationUnitController {
    private final CboDonorIpOrganisationUnitService cboDonorIpOrganisationUnitService;

    @GetMapping
    public ResponseEntity<List<CboDonorIpOrganisationUnitDTO>> getAllCboDonorIpOrganisationUnits() {
        return ResponseEntity.ok(cboDonorIpOrganisationUnitService.getAllCboDonorIpOrganisationUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CboDonorIpOrganisationUnitDTO> getCbo(@PathVariable Long id) {
        return ResponseEntity.ok(cboDonorIpOrganisationUnitService.getCboDonorIpOrganisationUnit(id));
    }

    @PostMapping
    public ResponseEntity<CboDonorIpOrganisationUnit> save(@RequestBody CboDonorIpOrganisationUnitDTO cboDonorIpOrganisationUnitDTO) {
        return ResponseEntity.ok(cboDonorIpOrganisationUnitService.save(cboDonorIpOrganisationUnitDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CboDonorIpOrganisationUnit> updateCbo(@RequestBody CboDonorIpOrganisationUnitDTO cboDonorIpOrganisationUnitDTO, @PathVariable Long id) {
        return ResponseEntity.ok(cboDonorIpOrganisationUnitService.update(id, cboDonorIpOrganisationUnitDTO));
    }
}
