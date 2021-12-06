package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@EqualsAndHashCode
public class RolePermissionPK implements Serializable {
    @Column(name = "role_id")
    @Id
    private Long roleId;

    @Column(name = "permission_id")
    @Id
    private Long permissionId;

    @Column(name = "role_id")
    @Id
    public Long getRoleId() {
        return roleId;
    }
}
