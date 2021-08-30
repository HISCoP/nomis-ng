package org.nomisng.controller;


import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.service.OvcServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ovc-services")
public class OvcServiceController {
    private final OvcServiceService ovcServiceService;

    @PostMapping
    public ResponseEntity<OvcService> save(@RequestBody OvcServiceDTO ovcServiceDTO) {
        return ResponseEntity.ok(ovcServiceService.save(ovcServiceDTO));
    }

    @PutMapping("/{id}")
    public OvcService update(@PathVariable Long id, @RequestBody OvcServiceDTO ovcServiceDTO) {
        return ovcServiceService.update(id, ovcServiceDTO);
    }

    @GetMapping("{id}/domain")
    public ResponseEntity<Domain> getDomainByOvcServiceId(@PathVariable Long id) {
        return ResponseEntity.ok(ovcServiceService.getDomainByOvcServiceId(id));
    }

    @GetMapping
    public ResponseEntity<List<OvcServiceDTO>> getAllOvcServices() {
        return ResponseEntity.ok(ovcServiceService.getAllOvcServices());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ovcServiceService.delete(id));
    }
}
