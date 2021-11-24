package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.UserDTO;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.UserMapper;
import org.nomisng.repository.*;
import org.nomisng.security.RolesConstants;
//import org.nomisng.security.SecurityUtils;
import org.nomisng.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final ApplicationUserCboProjectRepository applicationUserCboProjectRepository;

    private final CboProjectRepository cboProjectRepository;


    @Transactional
    public Optional<User> getUserWithAuthoritiesByUsername(String userName){
        return userRepository.findOneWithRoleByUserName(userName);
    }

    @Transactional(readOnly = true)
    public  Optional<User> getUserWithRoles(){
           return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithRoleByUserName);
    }

    public User registerUser(UserDTO userDTO, String password, Boolean updateUser){
        Optional<User> optionalUser = userRepository.findOneByUserName(userDTO.getUserName().toLowerCase());
        User newUser = new User();
        if(updateUser){
        }else {
            optionalUser.ifPresent(existingUser-> {
                        throw new RecordExistException(User.class, "Record exist", userDTO.getUserName().toLowerCase() + "");
                    }
            );
        }
        Long cboProjectId = getUserWithRoles().get().getCurrentCboProjectId();

        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setUserName(userDTO.getUserName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setGender(userDTO.getGender());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setCurrentCboProjectId(cboProjectId);

        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findAll().stream()
                    .filter(name -> RolesConstants.USER.equals(name.getName()))
                    .findAny()
                    .orElse(null);
            if(role !=null)
                roles.add(role);
            newUser.setRole(roles);
        } else {
            newUser.setRole(getRolesFromStringSet(userDTO.getRoles()));
        }

        userRepository.save(newUser);
        //log.debug("User Created: {}", newUser);
        return newUser;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    public User update(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent())throw new EntityNotFoundException(User.class, "Id", id +"");
        user.setId(id);
        return userRepository.save(user);
    }

    private HashSet<Role> getRolesFromStringSet(Set<String> roles) {
        HashSet roleSet = new HashSet<>();
        Role roleToAdd = new Role();
        for(String r : roles){
            // add roles by either id or name
            if(null != r) {
                roleToAdd = roleRepository.findByName(r).get();
                if (null == roleToAdd && NumberUtils.isParsable(r))
                    roleToAdd = roleRepository.findById(Long.valueOf(r)).get();
            } else {
                ResponseEntity.badRequest();
                return null;
            }
            roleSet.add(roleToAdd);
        }
        return roleSet;
    }

    @Transactional
    public List<UserDTO> getAllUserByRole(Long roleId){
        HashSet<Role> roles = new HashSet<>();
        Optional<Role> role = roleRepository.findById(roleId);
        roles.add(role.get());

        return userMapper.usersToUserDTOs(userRepository.findAllByRoleIn(roles));
    }

    public UserDTO changeOrganisationUnit(Long cboProjectId, UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());

        boolean found = false;
        for (ApplicationUserCboProject applicationUserCboProject : userDTO.getApplicationUserCboProjects()) {
            Long projectId = applicationUserCboProject.getCboProjectId();
            if(cboProjectId.longValue() == projectId.longValue()){
                found = true;
                break;
            }
        }
        if(!found){
            throw new EntityNotFoundException(OrganisationUnit.class, "Id", cboProjectId +"");
        }
        User user = optionalUser.get();
        user.setCurrentCboProjectId(cboProjectId);
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    public List<CboProject> getCboProjectByUserId(Long userId) {
        return applicationUserCboProjectRepository.findAllByApplicationUserId(userId).stream()
                .map(ApplicationUserCboProject::getCboProjectByCboProjectId)
                .collect(Collectors.toList());
    }
}