package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.VisitDTO;
import org.nomisng.domain.entity.Visit;
import org.nomisng.service.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @GetMapping
    public ResponseEntity<List<VisitDTO>> getAllVisits() {
        return ResponseEntity.ok(visitService.getAllVisits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDTO> getVisit(@PathVariable Long id) {
        return ResponseEntity.ok(visitService.getVisit(id));
    }

    @PostMapping
    public ResponseEntity<Visit> save(@Valid @RequestBody VisitDTO visitDTO) {
        return ResponseEntity.ok(visitService.save(visitDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(@Valid @RequestBody VisitDTO visitDTO, @PathVariable Long id) {
        return ResponseEntity.ok(visitService.update(id, visitDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        visitService.delete(id);
    }
}
