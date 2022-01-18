package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.FormFlagDTOS;
import org.nomisng.domain.entity.FormFlag;
import org.nomisng.service.FormFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flags")
@Slf4j
@RequiredArgsConstructor
public class FlagController {
    private final FormFlagService formFlagService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<List<FormFlagDTOS>> getAllFlags() {
        return ResponseEntity.ok(formFlagService.getAllFlags());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<FormFlagDTOS> getFlagById(@PathVariable Long id) {
        return ResponseEntity.ok(formFlagService.getFlagById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<List<FormFlag>> save(@RequestBody FormFlagDTOS formFlagDTOS) {
        return ResponseEntity.ok(formFlagService.save(formFlagDTOS));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<FormFlagDTOS> update(@PathVariable Long id, @RequestBody FormFlagDTOS formFlagDTOS) {
        return ResponseEntity.ok(formFlagService.update(id, formFlagDTOS));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        formFlagService.delete(id);
    }
}
