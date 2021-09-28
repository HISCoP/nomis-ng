package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.Donor;
import org.nomisng.service.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Donor> save(@RequestBody DonorDTO donorDTO) {
        return ResponseEntity.ok(donorService.save(donorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donor> updateDonor(@RequestBody DonorDTO donorDTO, @PathVariable Long id) {
        return ResponseEntity.ok(donorService.update(id, donorDTO));
    }
}
