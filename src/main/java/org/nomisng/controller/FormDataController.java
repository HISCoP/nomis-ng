package org.nomisng.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.FormData;
import org.nomisng.service.FormDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/form-data")
@Slf4j
@RequiredArgsConstructor
public class FormDataController {
    private final FormDataService formDataService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<List<FormData>> getAllFormData() {
        return ResponseEntity.ok(formDataService.getAllFormData());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<FormData> getFormData(@PathVariable Long id) {
        return ResponseEntity.ok(formDataService.getFormData(id));
    }

    /*@PostMapping
    public ResponseEntity<FormData> save(@RequestBody FormData formData) {
        return ResponseEntity.ok(formDataService.save(formData));

    }*/

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public ResponseEntity<FormData> update(@PathVariable Long id, @Valid @RequestBody FormDataDTO formDataDTO) {
        return ResponseEntity.ok(formDataService.update(id, formDataDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC', 'M&E Officer')")
    public void delete(@PathVariable Long id) {
        formDataService.delete(id);
    }
}
