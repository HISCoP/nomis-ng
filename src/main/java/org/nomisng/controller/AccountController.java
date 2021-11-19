package org.nomisng.controller;

import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.vm.ManagedUserVM;
import org.nomisng.domain.dto.UserDTO;
import org.nomisng.domain.entity.User;
import org.nomisng.repository.UserRepository;
import org.nomisng.service.UserService;
import org.nomisng.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserRepository userRepository;

    private final UserService userService;

    public AccountController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/account")
    public UserDTO getAccount(Principal principal){
        UserDTO userDTO =  userService
                .getUserWithRoles()
                .map(UserDTO::new)
                .orElseThrow(() -> new EntityNotFoundException(User.class, principal.getName()+"","" ));

        return userDTO;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        //Check Password Length
        userService.registerUser(managedUserVM, managedUserVM.getPassword(), false);
    }

    @GetMapping("/users")
    //@PreAuthorize("hasAnyRole('DEC', 'System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
