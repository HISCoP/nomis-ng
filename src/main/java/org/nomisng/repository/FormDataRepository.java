package org.nomisng.repository;

import org.nomisng.domain.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FormDataRepository extends JpaRepository<FormData, Long>, JpaSpecificationExecutor {
    Optional<FormData> findByIdAndArchived(Long id, int archived);

    //Optional<FormData> findByIdAndArchived(Long id, int archived, Long organisationUnitId);

    List<FormData> findAllByArchivedOrderByIdDesc(int archived);

    //List<FormData> findAllByEncounterIdAndArchivedAndOrganisationUnitIdOrderByIdDesc(Long encounterId, int archived, Long organisationUnitId);

}

