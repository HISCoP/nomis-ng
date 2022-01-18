package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.entity.Permission;
import org.nomisng.repository.PermissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionRepository permissionRepository;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC')")
    public ResponseEntity<List<Permission>> getAll() {
        return ResponseEntity.ok(this.permissionRepository.findAllByArchived(0));
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('System Administrator','Administrator', 'DEC')")
    public ResponseEntity<List<Permission>> save(@Valid @RequestBody List<Permission> permissions) {
        return ResponseEntity.ok(this.permissionRepository.saveAll(permissions));
    }
}
