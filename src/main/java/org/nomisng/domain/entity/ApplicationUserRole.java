package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "application_user_role")
@IdClass(ApplicationUserRolePK.class)
public class ApplicationUserRole {
    private Long userId;
    private Long roleId;
    private ApplicationUser applicationUserByUserId;
    private Role roleByRoleId;

    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUserRole that = (ApplicationUserRole) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    public ApplicationUser getApplicationUserByUserId() {
        return applicationUserByUserId;
    }

    public void setApplicationUserByUserId(ApplicationUser applicationUserByUserId) {
        this.applicationUserByUserId = applicationUserByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }
}
