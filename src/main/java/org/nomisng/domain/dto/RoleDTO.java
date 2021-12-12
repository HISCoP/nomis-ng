package org.nomisng.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.nomisng.domain.entity.Permission;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private String code;
    private List<Permission> permissions;
}
