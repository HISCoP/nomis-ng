package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.ImplementerDTO;
import org.nomisng.domain.entity.Implementer;
import org.nomisng.service.ImplementerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/implementers")
@RequiredArgsConstructor
public class ImplementerController {
    private final ImplementerService implementerService;

    @GetMapping
    public ResponseEntity<List<ImplementerDTO>> getAllImplementers() {
        return ResponseEntity.ok(implementerService.getAllImplementers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImplementerDTO> getImplementer(@PathVariable Long id) {
        return ResponseEntity.ok(implementerService.getImplementer(id));
    }

    @PostMapping
    //@PreAuthorize("hasAnyRole('System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<Implementer> save(@Valid @RequestBody ImplementerDTO implementerDTO) {
        return ResponseEntity.ok(implementerService.save(implementerDTO));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('DEC', 'System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<Implementer> update(@PathVariable Long id, @Valid @RequestBody ImplementerDTO implementerDTO) {
        return ResponseEntity.ok(implementerService.update(id, implementerDTO));
    }
}
