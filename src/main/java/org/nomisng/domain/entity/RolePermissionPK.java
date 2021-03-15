package org.nomisng.domain.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RolePermissionPK implements Serializable {
    private Long roleId;
    private Long permissionId;

    @Column(name = "role_id")
    @Id
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "permission_id")
    @Id
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionPK that = (RolePermissionPK) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }
}
