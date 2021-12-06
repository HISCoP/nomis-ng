package org.nomisng.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.nomisng.domain.entity.ApplicationUserCboProject;
import org.nomisng.domain.entity.Permission;
import org.nomisng.domain.entity.Role;
import org.nomisng.domain.entity.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private Set<String> roles;
    private Set<String> permissions;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private Long currentCboProjectId;
    private Long cboProjectId;
    @ToString.Exclude
    private List<ApplicationUserCboProject> applicationUserCboProjects;
    private String currentCboProjectDescription;



    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.roles = user.getRole().stream().map(Role::getName).collect(Collectors.toSet());
        permissions = new HashSet<>();
        user.getRole().forEach(roles1 ->{
            permissions.addAll(roles1.getPermission().stream().map(Permission::getName).collect(Collectors.toSet()));
        });
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.currentCboProjectId = user.getCurrentCboProjectId();
        this.applicationUserCboProjects = user.getApplicationUserCboProjects();
        this.currentCboProjectDescription = user.getCboProjectByCurrentCboProjectId() != null ? user.getCboProjectByCurrentCboProjectId().getDescription() : null;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", currentCboProjectId='" + currentCboProjectId + '\'' +
                ", cboProjectId='" + cboProjectId + '\'' +
                ", roles=" + roles + '\'' +
                ", permissions=" + permissions + '\'' +
                '}';
    }
}
