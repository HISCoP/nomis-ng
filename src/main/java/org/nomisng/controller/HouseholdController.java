package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMigration;
import org.nomisng.service.EncounterService;
import org.nomisng.service.HouseholdService;
import org.nomisng.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/households")
@RequiredArgsConstructor
public class HouseholdController {
    private final HouseholdService householdService;
    private final EncounterService encounterService;


    //@RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public ResponseEntity<List<HouseholdDTO>> getAllHouseholds(@RequestParam (required = false, defaultValue = "*") String search,
                                                               @PageableDefault(value = 100) Pageable pageable) {
        Page<Household> householdPage = householdService.getAllHouseholdsByPage(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), householdPage);
        return new ResponseEntity<>(householdService.getAllHouseholdsFromPage(householdPage), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/encounters")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getEncounterByHouseholdId(id));
    }

    @GetMapping("/{id}/{formCode}/encounters")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdIdAndFormCode(@PathVariable Long id, @PathVariable String formCode,
                                                                                    @RequestParam(required = false, defaultValue = "*")String dateFrom,
                                                                                    @RequestParam(required = false, defaultValue = "*")String dateTo,
                                                                                    @PageableDefault(value = 100) Pageable pageable) {
        Page<Encounter> encounterPage;
        if((dateFrom != null && !dateFrom.equalsIgnoreCase("*")) && (dateTo != null || !dateTo.equalsIgnoreCase("*"))){
            encounterPage = encounterService.getEncounterByHouseholdIdAndFormCodeAndDateEncounter(id, formCode, dateFrom, dateTo, pageable);
        } else {
            encounterPage = encounterService.getEncountersByHouseholdIdAndFormCode(id, formCode, pageable);
        }
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

    //@RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/{id}")
    public ResponseEntity<HouseholdDTO> getHouseholdById(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdById(id));
    }

    @GetMapping("/{id}/householdMembers")
    public ResponseEntity<List<HouseholdMemberDTO>> getHouseholdMembersByHouseholdId(@PathVariable Long id,
                                                                                     @RequestParam(required = false, defaultValue = "0") Integer memberType) {
        return ResponseEntity.ok(householdService.getHouseholdMembersByHouseholdId(id, memberType));
    }


    @GetMapping("/{id}/householdAddress")
    public ResponseEntity<List<HouseholdMigrationDTO>> getHouseholdAddressesByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdAddressesByHouseholdId(id));
    }

    //@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping
    public ResponseEntity<Household> save(@RequestBody @Valid HouseholdDTO householdDTO) {
        return ResponseEntity.ok(householdService.save(householdDTO));
    }

    @PostMapping("/{id}/householdMigration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<HouseholdMigrationDTO>> saveHouseholdAddress(@PathVariable Long id, @Valid @RequestBody HouseholdMigration householdMigration) {
        return ResponseEntity.ok(householdService.saveHouseholdMigration(id, householdMigration));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Household> update(@Valid @RequestBody HouseholdDTO householdDTO, @PathVariable Long id) {
        return ResponseEntity.ok(householdService.update(id, householdDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        householdService.delete(id);
    }

    /*@GetMapping("/organisation-unit/{id}")
    public ResponseEntity<Long> getHouseholdIdForWard(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getMaxHouseholdIdByOrganisation(id));
    }*/
}
