package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "permission")
public class Permission {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "permissionByPermissionId")
    @ToString.Exclude
    private Collection<RolePermission> rolePermissionsById;
}
