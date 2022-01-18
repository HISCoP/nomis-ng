package org.nomisng.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.nomisng.controller.apierror.IllegalTypeException;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.User;
import org.nomisng.repository.UserRepository;
import org.nomisng.security.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.NoSuchElementException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private  final UserRepository userRepository;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getRequestURI().contains("/api/") &&  !request.getRequestURI().contains("/api/authenticate") && !request.getRequestURI().contains("/api/cbo-projects/all") &&
                !request.getRequestURI().contains("/api/application-codesets/codesetGroup")) {
            log.info("User "+request.getRemoteUser() + " " + request.getMethod() + " Request URL {}", request.getRequestURI());
            try {
                if (SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithRoleByUserName).get().getCurrentCboProjectId() == null) {
                    throw new IllegalTypeException(CboProject.class, "cboProjectId", "user cbo project id invalid, login  or switch to right cbo project");
                }
            } catch (NoSuchElementException e){
                throw new NoSuchElementException(e.getMessage());
            }

            return true;
        }
        else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        //System.out.println("Post Handle method is Calling");
    }

    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) throws Exception {

        //System.out.println("Request and Response is completed");
    }

    private boolean HandleEncounterController(@NotNull HttpServletRequest request) throws ResponseStatusException {
        Principal principal = request.getUserPrincipal();
        return true;
    }
}