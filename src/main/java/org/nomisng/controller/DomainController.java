package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.audit4j.core.annotation.Audit;
import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.service.DomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/domains")
@Slf4j
@RequiredArgsConstructor
@Audit
public class DomainController {
    private final DomainService domainService;

    @GetMapping
    public ResponseEntity<List<DomainDTO>> getAllDomains() {
        return ResponseEntity.ok(domainService.getAllDomains());
    }

    @GetMapping("/domainCode")
    public ResponseEntity<DomainDTO> getFormByFormCode(@RequestParam String domainCode) {
            return ResponseEntity.ok(domainService.getDomainByDomainCode(domainCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomainDTO> getForm(@PathVariable Long id) {
        return ResponseEntity.ok(domainService.getDomain(id));
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
