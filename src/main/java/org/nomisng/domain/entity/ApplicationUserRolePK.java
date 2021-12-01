package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@EqualsAndHashCode
@Data
public class ApplicationUserRolePK implements Serializable {
    @Column(name = "user_id")
    @Id
    private Long userId;

    @Column(name = "role_id")
    @Id
    private Long roleId;
}
