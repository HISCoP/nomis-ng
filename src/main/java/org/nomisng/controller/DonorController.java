package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.Donor;
import org.nomisng.service.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/donors")
@RequiredArgsConstructor
public class DonorController {
    private final DonorService donorService;

    @GetMapping
    public ResponseEntity<List<DonorDTO>> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorDTO> getDonor(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getDonor(id));
    }

    @PostMapping
    public ResponseEntity<Donor> save(@Valid @RequestBody DonorDTO donorDTO) {
        return ResponseEntity.ok(donorService.save(donorDTO));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<Donor> update(@PathVariable Long id, @Valid @RequestBody DonorDTO donorDTO) {
        return ResponseEntity.ok(donorService.update(id, donorDTO));
    }
}
