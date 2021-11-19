package org.nomisng.repository;

import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.CboProjectLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CboProjectLocationRepository extends JpaRepository<CboProjectLocation, Long>, JpaSpecificationExecutor {

    @Query(value = "SELECT * FROM cbo_project_location WHERE cbo_project_id = ?1 " +
            "AND organisation_unit_id IN (?2)", nativeQuery = true)
    List<CboProjectLocation> findAllByIdAndOrganisationUnitIn(Long cboProjectId, List<Long> organisationUnitIds);

    @Query(value = "SELECT * FROM cbo_project_location WHERE cbo_project_id = ?1", nativeQuery = true)
    List<CboProjectLocation> findAllById(Long cboProjectId);

}

