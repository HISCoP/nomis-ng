package org.nomisng.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.ApplicationCodesetDTO;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.service.ApplicationCodesetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/application-codesets")
@Slf4j
@RequiredArgsConstructor
public class ApplicationCodesetController {
    private final ApplicationCodesetService applicationCodesetService;

    @GetMapping("/codesetGroup/{codesetGroup}")
    public ResponseEntity<List<ApplicationCodesetDTO>> getApplicationCodeByCodesetGroup(@PathVariable String codesetGroup) {
        return ResponseEntity.ok(applicationCodesetService.getApplicationCodeByCodesetGroup(codesetGroup));
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<ApplicationCodesetDTO> getApplicationCodesetById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationCodesetService.getApplicationCodesetById(id));
    }*/

    @GetMapping
    public ResponseEntity<List<ApplicationCodesetDTO>> getAllApplicationCodesets() {
        return ResponseEntity.ok(applicationCodesetService.getAllApplicationCodeset());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC')")
    public ResponseEntity<ApplicationCodeset> save(@Valid @RequestBody ApplicationCodesetDTO applicationCodesetDTO) {
        return ResponseEntity.ok(applicationCodesetService.save(applicationCodesetDTO));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC')")
    public ResponseEntity<ApplicationCodeset> update(@PathVariable Long id, @Valid @RequestBody ApplicationCodesetDTO applicationCodesetDTO) {
        return ResponseEntity.ok(applicationCodesetService.update(id, applicationCodesetDTO));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC')")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(applicationCodesetService.delete(id));
    }
}
