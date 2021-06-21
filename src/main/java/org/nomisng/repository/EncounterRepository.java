package org.nomisng.repository;

import org.nomisng.domain.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long>, JpaSpecificationExecutor {

}

