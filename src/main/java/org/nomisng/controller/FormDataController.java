package org.nomisng.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.FormData;
import org.nomisng.service.FormDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<FormData>> getAllFormData() {
        return ResponseEntity.ok(formDataService.getAllFormData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormData> getFormData(@PathVariable Long id) {
        return ResponseEntity.ok(formDataService.getFormData(id));
    }

    /*@PostMapping
    public ResponseEntity<FormData> save(@RequestBody FormData formData) {
        return ResponseEntity.ok(formDataService.save(formData));

    }*/

    @PutMapping("{id}")
    public ResponseEntity<FormData> update(@PathVariable Long id, @Valid @RequestBody FormDataDTO formDataDTO) {
        return ResponseEntity.ok(formDataService.update(id, formDataDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        formDataService.delete(id);
    }
}
