package org.nomisng.controller;


import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.ProgramDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.Program;
import org.nomisng.service.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/programs")
public class ProgramController {
    private final ProgramService programService;

    @PostMapping
    public ResponseEntity<Program> save(@RequestBody ProgramDTO programDTO) {
        return ResponseEntity.ok(programService.save(programDTO));
    }

    @PutMapping("/{id}")
    public Program update(@PathVariable Long id, @RequestBody ProgramDTO programDTO) {
        return programService.update(id, programDTO);
    }

    @GetMapping("{id}/forms")
    public ResponseEntity<List<Form>> getFormByProgramId(@PathVariable Long id) {
            return ResponseEntity.ok(programService.getFormByProgramId(id));
    }

    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(programService.delete(id));
    }
}
