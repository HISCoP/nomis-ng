package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.service.DomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/domains")
@Slf4j
@RequiredArgsConstructor
public class DomainController {
    private final DomainService domainService;

    @GetMapping
    public ResponseEntity<List<DomainDTO>> getAllDomains() {
        return ResponseEntity.ok(domainService.getAllDomains());
    }

    @GetMapping("/domainCode")
    public ResponseEntity<DomainDTO> getDomainByFormCode(@RequestParam String domainCode) {
            return ResponseEntity.ok(domainService.getDomainByDomainCode(domainCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomainDTO> getDomainById(@PathVariable Long id) {
        return ResponseEntity.ok(domainService.getDomainById(id));
    }

    @GetMapping("{id}/ovcServices")
    public ResponseEntity<List<OvcServiceDTO>> getOvcServicesByDomainId(@PathVariable Long id) {
        return ResponseEntity.ok(domainService.getOvcServicesByDomainId(id));
    }

    @GetMapping("{id}/ovcServices/{serviceType}")
    public ResponseEntity<List<OvcServiceDTO>> getOvcServicesByDomainIdAndServiceType(@PathVariable Long id, @PathVariable Integer serviceType) {
        return ResponseEntity.ok(domainService.getOvcServicesByDomainIdAndServiceType(id, serviceType));
    }

    @PostMapping
    public ResponseEntity<Domain> save(@RequestBody DomainDTO domainDTO) {
        return ResponseEntity.ok(domainService.save(domainDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Domain> update(@PathVariable Long id, @RequestBody DomainDTO domainDTO) {
        return ResponseEntity.ok(domainService.update(id, domainDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(domainService.delete(id));
    }
}
