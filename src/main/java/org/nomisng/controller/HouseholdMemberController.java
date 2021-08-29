package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.service.HouseholdMemberService;
import org.nomisng.service.HouseholdService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/household-members")
@RequiredArgsConstructor
public class HouseholdMemberController {
    private final HouseholdMemberService householdMemberService;

    @GetMapping
    public ResponseEntity<List<HouseholdMemberDTO>> getAllHouseholds() {
        return ResponseEntity.ok(householdMemberService.getAllHouseholdMembers());
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


    @PostMapping
    public ResponseEntity<HouseholdMember> save(@RequestBody HouseholdMemberDTO householdMemberDTO) {
        return ResponseEntity.ok(householdMemberService.save(householdMemberDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseholdMember> update(@RequestBody HouseholdMemberDTO householdMemberDTO, @PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.update(id, householdMemberDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        householdMemberService.delete(id);
    }
}
