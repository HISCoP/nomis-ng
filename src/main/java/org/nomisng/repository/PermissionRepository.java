package org.nomisng.repository;

import org.nomisng.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor {
}

