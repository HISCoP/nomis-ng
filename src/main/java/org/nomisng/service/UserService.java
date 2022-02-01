package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.nomisng.controller.apierror.AccessDeniedException;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.UserDTO;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.UserMapper;
import org.nomisng.repository.*;
import org.nomisng.security.RolesConstants;
//import org.nomisng.security.SecurityUtils;
import org.nomisng.security.SecurityUtils;
import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ApplicationUserRoleRepository applicationUserRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final ApplicationUserCboProjectRepository applicationUserCboProjectRepository;


    @Transactional
    public Optional<User> getUserWithAuthoritiesByUsername(String userName) {
        return userRepository.findOneWithRoleByUserNameAndArchived(userName, UN_ARCHIVED);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithRoles() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithRoleByUserName);
    }

    public User save(UserDTO userDTO, String password, Boolean updateUser) {
        Optional<User> optionalUser = userRepository.findOneByUserNameAndArchived(userDTO.getUserName().toLowerCase(), UN_ARCHIVED);
        User newUser = new User();
        if (updateUser) {
        } else {
            optionalUser.ifPresent(existingUser -> {
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
            if (role != null)
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
        return userRepository.findAllByArchived(UN_ARCHIVED, pageable).map(UserDTO::new);
    }

    public User update(Long id, User user) {
        Optional<User> optionalUser = userRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if (!optionalUser.isPresent()) throw new EntityNotFoundException(User.class, "Id", id + "");
        user.setId(id);
        return userRepository.save(user);
    }

    private HashSet<Role> getRolesFromStringSet(Set<String> roles) {
        HashSet roleSet = new HashSet<>();
        Role roleToAdd = new Role();
        for (String r : roles) {
            // add roles by either id or name
            if (null != r) {
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
    public List<UserDTO> getAllUserByRole(Long roleId) {
        HashSet<Role> roles = new HashSet<>();
        Optional<Role> role = roleRepository.findById(roleId);
        roles.add(role.get());

        return userMapper.usersToUserDTOs(userRepository.findAllByRoleIn(roles).stream()
                .filter(user -> (user.getArchived() == UN_ARCHIVED))
                .collect(Collectors.toList()));

    }

    /*public UserDTO changeOrganisationUnit(Long cboProjectId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());

        boolean found = false;
        for (ApplicationUserCboProject applicationUserCboProject : userDTO.getApplicationUserCboProjects()) {
            Long projectId = applicationUserCboProject.getCboProjectId();
            if (cboProjectId.longValue() == projectId.longValue()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new EntityNotFoundException(OrganisationUnit.class, "Id", cboProjectId + "");
        }
        User user = optionalUser.get();
        user.setCurrentCboProjectId(cboProjectId);
        return userMapper.userToUserDTO(userRepository.save(user));
    }*/

    public List<CboProject> getCboProjectByUserId(Long userId) {
        return applicationUserCboProjectRepository.findAllByApplicationUserIdOrderByIdAsc(userId).stream()
                .map(ApplicationUserCboProject::getCboProjectByCboProjectId)
                .collect(Collectors.toList());
    }

    public UserDTO getAccount(String userName) {
        UserDTO userDTO = this.getUserWithRoles()
                .map(UserDTO::new)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userName + "", ""));
        return userDTO;
    }

    public List<Role> getRolesByUserId(Long id) {
        List<Role> roles = new ArrayList<>();
        applicationUserRoleRepository.findAllByUserId(id).forEach(applicationUserRole -> {
            roles.add(applicationUserRole.getRoleByRoleId());
        });

        return roles;
    }

    public Set<Role> updateRoles(Long id, List<Role> roles) {
            User user = userRepository.findByIdAndArchived(id, UN_ARCHIVED).get();
            HashSet rolesSet = new HashSet<>();
            Role roleToAdd = new Role();
            for (Role r : roles) {
                // add roles by either id or name
                if (r.getName() != null) {
                    roleToAdd = roleRepository.findByName(r.getName()).get();
                } else if (r.getId() != null) {
                    roleToAdd = roleRepository.findById(r.getId()).get();
                } else {
                    ResponseEntity.badRequest();
                    return null;
                }
                rolesSet.add(roleToAdd);
            }
            user.setRole(rolesSet);
            this.update(id, user);
            return user.getRole();
    }

    public void delete(Long id) {
        Set<String> permissions = new HashSet<>();
        this.getUserWithRoles().get().getRole().forEach(roles1 ->{
            permissions.addAll(roles1.getPermission().stream().map(Permission::getName).collect(Collectors.toSet()));
        });

        if(permissions.contains("user_delete") || permissions.contains("permission_all")) {
            List<ApplicationUserRole> applicationUserRoles = applicationUserRoleRepository.findAllByUserId(id);
            applicationUserRoleRepository.deleteAll(applicationUserRoles);


            List<ApplicationUserCboProject> applicationUserCboProjects = applicationUserCboProjectRepository.findAllByApplicationUserIdOrderByIdAsc(id);
            if (!applicationUserCboProjects.isEmpty()) {
                applicationUserCboProjectRepository.deleteAll(applicationUserCboProjects);
            }

            User user = userRepository.getOne(id);
            user.setArchived(ARCHIVED);
            userRepository.save(user);
        } else {
            throw new AccessDeniedException(Permission.class, "Delete", "Delete");
        }
    }
}