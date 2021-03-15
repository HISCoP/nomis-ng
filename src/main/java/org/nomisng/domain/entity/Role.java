package org.nomisng.domain.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Role {
    private Long id;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private String name;
    private Collection<ApplicationUserRole> applicationUserRolesById;
    private Collection<RolePermission> rolePermissionsById;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_created")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "date_modified")
    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(dateCreated, role.dateCreated) &&
                Objects.equals(dateModified, role.dateModified) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated, dateModified, name);
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<ApplicationUserRole> getApplicationUserRolesById() {
        return applicationUserRolesById;
    }

    public void setApplicationUserRolesById(Collection<ApplicationUserRole> applicationUserRolesById) {
        this.applicationUserRolesById = applicationUserRolesById;
    }

    @OneToMany(mappedBy = "roleByRoleId")
    public Collection<RolePermission> getRolePermissionsById() {
        return rolePermissionsById;
    }

    public void setRolePermissionsById(Collection<RolePermission> rolePermissionsById) {
        this.rolePermissionsById = rolePermissionsById;
    }
}
