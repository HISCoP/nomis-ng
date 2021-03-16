package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "role")
public class Role extends Audit<String> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "roleByRoleId")
    @ToString.Exclude
    private Collection<ApplicationUserRole> applicationUserRolesById;

    @OneToMany(mappedBy = "roleByRoleId")
    @ToString.Exclude
    private Collection<RolePermission> rolePermissionsById;
}
