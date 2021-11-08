package org.nomisng.repository;

import org.nomisng.domain.entity.Implementer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplementerRepository extends JpaRepository<Implementer, Long>, JpaSpecificationExecutor {

}

