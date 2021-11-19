package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
//import org.nomisng.security.SecurityUtils;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "application_user")
public class User {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "user_name")
    @NonNull
    private String userName;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;

    @Basic
    @Column(name = "gender")
    private String gender;

    @Basic
    @Column(name = "password")
    @NonNull
    private String password;

    @Basic
    @Column(name = "archived")
    @NonNull
    private Integer archived = 0;

    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private String createdBy = "guest@nomis-ng.org";//SecurityUtils.getCurrentUserLogin().orElse(null);

    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "modified_by")
    @JsonIgnore
    @ToString.Exclude
    private String modifiedBy = "guest@nomis-ng.org";//SecurityUtils.getCurrentUserLogin().orElse(null);

    @Column(name = "date_modified")
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateModified = LocalDateTime.now();

    @Basic
    @Column(name = "activation_key")
    private String activationKey;

    @Basic
    @Column(name = "date_reset")
    private Date dateReset;

    @Basic
    @Column(name = "reset_key")
    private String resetKey;

    @Basic
    @Column(name = "current_cbo_project_id")
    private Long currentCboProjectId;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Role> role;

    @OneToMany(mappedBy = "applicationUserByApplicationUserId", cascade = CascadeType.PERSIST)
    private List<ApplicationUserCboProject> applicationUserCboProjects;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "current_cbo_project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CboProject cboProjectByCurrentCboProjectId;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "current_cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    private CboProject cboProjectByCboId;

    @OneToMany(mappedBy = "applicationUserByApplicationUserId")
    @JsonIgnore
    @ToString.Exclude
    public List<ApplicationUserCboProject> applicationUserCboProjectsById;
}