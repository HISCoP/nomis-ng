package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.VisitDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.Visit;
import org.nomisng.service.HouseholdService;
import org.nomisng.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/households")
@RequiredArgsConstructor
public class HouseholdController {
    private final HouseholdService householdService;

    @GetMapping
    public ResponseEntity<List<HouseholdDTO>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.getAllHouseholds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseholdDTO> getHouseholdById(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdById(id));
    }

    @PostMapping
    public ResponseEntity<Household> save(@RequestBody HouseholdDTO householdDTO) {
        return ResponseEntity.ok(householdService.save(householdDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Household> update(@RequestBody HouseholdDTO householdDTO, @PathVariable Long id) {
        return ResponseEntity.ok(householdService.update(id, householdDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.delete(id));
    }
}
