package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdAddress;
import org.nomisng.domain.entity.Visit;
import org.nomisng.service.EncounterService;
import org.nomisng.service.HouseholdService;
import org.nomisng.service.VisitService;
import org.nomisng.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/households")
@RequiredArgsConstructor
public class HouseholdController {
    private final HouseholdService householdService;
    private final EncounterService encounterService;


    @GetMapping
    public ResponseEntity<List<HouseholdDTO>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.getAllHouseholds());
    }

    @GetMapping("/{id}/encounters")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getEncounterByHouseholdId(id));
    }

    @GetMapping("/{id}/{formCode}/encounters")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdIdAndFormCode(@PathVariable Long id,
                                                                                          @PathVariable String formCode,
                                                                                          @PageableDefault(value = 100) Pageable pageable) {
        Page<Encounter> encounterPage = encounterService.getEncountersByHouseholdIdAndFormCode(id, formCode, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), encounterPage);
        return new ResponseEntity<>(encounterService.getEncounterDTOFromPage(encounterPage), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/{formCode}/formData")
    public ResponseEntity<List<FormDataDTO>> getFormDataByHouseholdIdAndFormCode(@PathVariable Long id,
                                                                                    @PathVariable String formCode,
                                                                                    @PageableDefault(value = 100) Pageable pageable) {
        Page<Encounter> encounterPage = encounterService.getEncountersByHouseholdIdAndFormCode(id, formCode, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), encounterPage);
        return new ResponseEntity<>(encounterService.getFormDataDTOFromPage(encounterPage), headers, HttpStatus.OK);
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
    public ResponseEntity<List<HouseholdAddressDTO>> getHouseholdAddressesByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdAddressesByHouseholdId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Household> save(@RequestBody HouseholdDTO householdDTO) {
        return ResponseEntity.ok(householdService.save(householdDTO));
    }

    @PostMapping("/{id}/householdAddress")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<HouseholdAddressDTO>> saveHouseholdAddress(@PathVariable Long id, @Valid @RequestBody HouseholdAddress householdAddress) {
        return ResponseEntity.ok(householdService.saveHouseholdAddress(id, householdAddress));
    }

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
