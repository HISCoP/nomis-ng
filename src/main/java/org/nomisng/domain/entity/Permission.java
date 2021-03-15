package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Permission {
    private Long id;
    private String description;
    private String name;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, name);
    }

    @OneToMany(mappedBy = "permissionByPermissionId")
    public Collection<RolePermission> getRolePermissionsById() {
        return rolePermissionsById;
    }

    public void setRolePermissionsById(Collection<RolePermission> rolePermissionsById) {
        this.rolePermissionsById = rolePermissionsById;
    }
}
