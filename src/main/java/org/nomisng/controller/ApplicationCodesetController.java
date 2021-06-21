package org.nomisng.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.ApplicationCodesetDTO;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.service.ApplicationCodesetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-codesets")
@Slf4j
@RequiredArgsConstructor
public class ApplicationCodesetController {
    private final ApplicationCodesetService applicationCodesetService;

    @GetMapping("/{codesetGroup}")
    public ResponseEntity<List<ApplicationCodesetDTO>> getApplicationCodeByCodesetGroup(@PathVariable String codesetGroup) {
        return ResponseEntity.ok(applicationCodesetService.getApplicationCodeByCodesetGroup(codesetGroup));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationCodesetDTO>> getAllApplicationCodesets() {
        return ResponseEntity.ok(applicationCodesetService.getAllApplicationCodeset());
    }

    @PostMapping
    public ResponseEntity<ApplicationCodeset> save(@RequestBody ApplicationCodesetDTO applicationCodesetDTO) {
        return ResponseEntity.ok(applicationCodesetService.save(applicationCodesetDTO));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationCodeset> update(@PathVariable Long id, @RequestBody ApplicationCodesetDTO applicationCodesetDTO) {
        return ResponseEntity.ok(applicationCodesetService.update(id, applicationCodesetDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(this.applicationCodesetService.delete(id));
    }
}
