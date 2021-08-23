package org.nomisng.repository;

import org.nomisng.domain.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long>, JpaSpecificationExecutor {

    List<Encounter> findAllByArchived(int unArchived);

    Optional<Encounter> findByIdAndArchived(Long id, int archived);
}

