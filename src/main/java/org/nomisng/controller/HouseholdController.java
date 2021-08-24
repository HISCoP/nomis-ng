package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdAddress;
import org.nomisng.domain.entity.Visit;
import org.nomisng.service.HouseholdService;
import org.nomisng.service.VisitService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}/encounters")
    //TODO: still in progress
    public ResponseEntity<List<Encounter>> getEncounterByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getEncounterByHouseholdId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseholdDTO> getHouseholdById(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdById(id));
    }

    @GetMapping("/{id}/householdMembers")
    public ResponseEntity<List<HouseholdMemberDTO>> getHouseholdMembersByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdMembersByHouseholdId(id));
    }


    @GetMapping("/{id}/householdAddress")
    public ResponseEntity<List<HouseholdAddressDTO>> getHouseholdContactsByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdContactsByHouseholdId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Household> save(@RequestBody HouseholdDTO householdDTO) {
        return ResponseEntity.ok(householdService.save(householdDTO));
    }

    //TODO: work on saving a houseAddress
    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Household> saveHouseholdAddress(@RequestBody HouseholdAddress householdAddress) {
        return ResponseEntity.ok(householdService.saveHouseholdAddress(householdDTO));
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Household> update(@RequestBody HouseholdDTO householdDTO, @PathVariable Long id) {
        return ResponseEntity.ok(householdService.update(id, householdDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        householdService.delete(id);
    }
}
