package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.service.FormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/forms")
@Slf4j
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;

    @GetMapping
    public ResponseEntity<List<FormDTO>> getAllForms() {
        return ResponseEntity.ok(formService.getAllForms());
    }

    @GetMapping("/formCode")
    public ResponseEntity<FormDTO> getFormByFormCode(@RequestParam String formCode) {
            return ResponseEntity.ok(formService.getFormByFormCode(formCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormDTO> getForm(@PathVariable Long id) {
        return ResponseEntity.ok(formService.getForm(id));
    }

    @PostMapping
    //@PreAuthorize("hasAnyRole('DEC', 'System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<Form> save(@Valid @RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.save(formDTO));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('DEC', 'System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<Form> update(@PathVariable Long id, @Valid @RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.update(id, formDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('DEC', 'System Administrator', 'Administrator', 'Admin')")
    public void delete(@PathVariable Long id) {
        formService.delete(id);
    }
}
