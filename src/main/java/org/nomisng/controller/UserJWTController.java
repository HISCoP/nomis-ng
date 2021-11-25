package org.nomisng.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.nomisng.controller.vm.LoginVM;
import org.nomisng.domain.entity.ApplicationUserCboProject;
import org.nomisng.domain.entity.User;
import org.nomisng.repository.ApplicationUserCboProjectRepository;
import org.nomisng.repository.UserRepository;
import org.nomisng.security.jwt.JWTFilter;
import org.nomisng.security.jwt.TokenProvider;
import org.nomisng.service.UserJWTService;
import org.nomisng.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserJWTController {
    private final UserJWTService userJWTService;
    private final UserService userService;
    private static final long DEFAULT_CBO_PROJECT_ID = 0L;


    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        String jwt = userJWTService.authorize(loginVM);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        //get user
        User user = userService.getUserWithAuthoritiesByUsername(loginVM.getUsername()).get();
        Long currentCboProjectId = loginVM.getCboProjectId();

        if(currentCboProjectId != DEFAULT_CBO_PROJECT_ID) {

            //get all cboProjectIds for the login user
            List<Long> cboProjectIds = user.getApplicationUserCboProjects()
                    .stream()
                    .map(ApplicationUserCboProject::getCboProjectId)
                    .collect(Collectors.toList());

            //if cboProject does not exist default the cboProject to zero(0)
            if (!cboProjectIds.contains(currentCboProjectId)) {
                currentCboProjectId = DEFAULT_CBO_PROJECT_ID;
            }
        }
        //set current cbo project
        user.setCurrentCboProjectId(currentCboProjectId);
        //update user
        userService.update(user.getId(), user);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {
        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
