package org.nomisng.controller;


import org.nomisng.domain.dto.RoleDTO;
import org.nomisng.domain.entity.Role;
import org.nomisng.repository.RoleRepository;
import org.nomisng.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public RoleController(RoleService roleService, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleRepository.findById(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role addRole(@Valid @RequestBody RoleDTO roleDTO) throws Exception {
        return roleService.save(roleDTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Role> update(@Valid @RequestBody RoleDTO role, @PathVariable Long id) {
        try {
            Role updatedRole = new Role();
            if (!role.getPermissions().isEmpty()){
                updatedRole = roleService.updatePermissions(id, role.getPermissions());
            }
            if (role.getName() != null){
                updatedRole = roleService.updateName(id, role.getName());
            }
            return ResponseEntity.ok(updatedRole);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRole(@PathVariable Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}
