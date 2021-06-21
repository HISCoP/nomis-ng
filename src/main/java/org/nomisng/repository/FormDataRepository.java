package org.nomisng.repository;

import org.nomisng.domain.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface FormDataRepository extends JpaRepository<FormData, Long>, JpaSpecificationExecutor {
}

