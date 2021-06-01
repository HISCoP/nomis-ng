package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "role_permission")
@IdClass(RolePermissionPK.class)
public class RolePermission {
    @Id
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Id
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role roleByRoleId;

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Permission permissionByPermissionId;
}
