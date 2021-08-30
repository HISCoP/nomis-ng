package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.CboDTO;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.service.CboService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cbos")
@RequiredArgsConstructor
public class CboController {
    private final CboService cboService;

    @GetMapping
    public ResponseEntity<List<CboDTO>> getAllCbos() {
        return ResponseEntity.ok(cboService.getAllCbos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CboDTO> getCbo(@PathVariable Long id) {
        return ResponseEntity.ok(cboService.getCbo(id));
    }

    @PostMapping
    public ResponseEntity<Cbo> save(@RequestBody CboDTO cboDTO) {
        return ResponseEntity.ok(cboService.save(cboDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cbo> updateCbo(@RequestBody CboDTO cboDTO, @PathVariable Long id) {
        return ResponseEntity.ok(cboService.update(id, cboDTO));
    }
}
