package org.nomisng.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "application_user")
public class ApplicationUser extends Audit {
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
    @Column(name = "active")
    @NonNull
    private Integer active = 1;

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
    @Column(name = "uploaded")
    private Integer uploaded;

    @Basic
    @Column(name = "time_uploaded")
    private Time timeUploaded;

    @Basic
    @Column(name = "current_organisation_unit_id")
    private Long currentOrganisationUnitId;

    @ManyToOne
    @JoinColumn(name = "current_organisation_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    private OrganisationUnit organisationUnitByCurrentOrganisationUnitId;

    @OneToMany(mappedBy = "applicationUserByUserId")
    @ToString.Exclude
    private Collection<ApplicationUserRole> applicationUserRolesById;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Role> role;
}
