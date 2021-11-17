package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.service.EncounterService;
import org.nomisng.service.HouseholdMemberService;
import org.nomisng.service.HouseholdService;
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
@RequestMapping("api/household-members")
@RequiredArgsConstructor
public class HouseholdMemberController {
    private final HouseholdMemberService householdMemberService;
    private final EncounterService encounterService;


    @GetMapping
    public ResponseEntity<List<HouseholdMemberDTO>> getAllHouseholds(@RequestParam (required = false, defaultValue = "*") String search,
                                                                     @PageableDefault(value = 100) Pageable pageable) {
        Page<HouseholdMember> householdMembersPage = householdMemberService.getAllHouseholdMembersPage(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), householdMembersPage);
        return new ResponseEntity<>(householdMemberService.getAllHouseholdMembersFromPage(householdMembersPage), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseholdMemberDTO> getHouseholdMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getHouseholdMemberById(id));
    }

    @GetMapping("/{id}/household")
    public ResponseEntity<HouseholdDTO> getHouseholdByHouseholdMemberId(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getHouseholdByHouseholdMemberId(id));
    }

    @GetMapping("/{id}/encounters")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdMemberId(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getEncountersByHouseholdMemberId(id));
    }

    @GetMapping("/{id}/{formCode}/encounters")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdMemberIdAndFormCode(@PathVariable Long id,
                                                                                          @PathVariable String formCode,
                                                                                          @RequestParam(required = false, defaultValue = "*")String dateFrom,
                                                                                          @RequestParam(required = false, defaultValue = "*")String dateTo,
                                                                                          @PageableDefault(value = 100) Pageable pageable) {
        Page<Encounter> encounterPage;
        if((dateFrom != null && !dateFrom.equalsIgnoreCase("*")) && (dateTo != null || !dateTo.equalsIgnoreCase("*"))){
            encounterPage = encounterService.getEncountersByHouseholdMemberIdAndFormCodeAndDateEncounter(id, formCode, dateFrom, dateTo, pageable);
        } else {
            encounterPage = encounterService.getEncountersByHouseholdMemberIdAndFormCode(id, formCode, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), encounterPage);
        return new ResponseEntity<>(encounterService.getEncounterDTOFromPage(encounterPage), headers, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<HouseholdMember> save(@Valid @RequestBody HouseholdMemberDTO householdMemberDTO) {
        return ResponseEntity.ok(householdMemberService.save(householdMemberDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseholdMember> update(@Valid @RequestBody HouseholdMemberDTO householdMemberDTO, @PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.update(id, householdMemberDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        householdMemberService.delete(id);
    }
}
