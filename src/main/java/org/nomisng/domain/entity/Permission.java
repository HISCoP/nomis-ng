package org.nomisng.domain.entity;

import lombok.*;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public class Permission extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private int archived;


    /*@OneToMany(mappedBy = "permissionByPermissionId")
    @ToString.Exclude
    @JsonIgnore
    public List<RolePermission> rolePermissionsById;*/
}