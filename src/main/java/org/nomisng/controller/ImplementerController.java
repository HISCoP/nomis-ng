package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.ImplementerDTO;
import org.nomisng.domain.entity.Implementer;
import org.nomisng.service.ImplementerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/implementers")
@RequiredArgsConstructor
public class ImplementerController {
    private final ImplementerService implementerService;

    @GetMapping
    public ResponseEntity<List<ImplementerDTO>> getAllIps() {
        return ResponseEntity.ok(implementerService.getAllIps());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImplementerDTO> getIp(@PathVariable Long id) {
        return ResponseEntity.ok(implementerService.getIp(id));
    }

    @PostMapping
    public ResponseEntity<Implementer> save(@Valid @RequestBody ImplementerDTO implementerDTO) {
        return ResponseEntity.ok(implementerService.save(implementerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Implementer> update(@PathVariable Long id, @Valid @RequestBody ImplementerDTO implementerDTO) {
        return ResponseEntity.ok(implementerService.update(id, implementerDTO));
    }
}
