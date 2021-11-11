package org.nomisng.repository;

import org.nomisng.domain.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long>, JpaSpecificationExecutor {
    @Query(value = "SELECT * FROM household WHERE id = ?1 AND archived = ?2 " +
            "AND organisation_unit_id IN (?3) ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Household> findByIdAndArchived(Long id, int archived, List<Long> organisationUnitIds);

    @Query(value = "SELECT * FROM household WHERE archived = ?1 " +
            "AND organisation_unit_id IN (?2) ORDER BY id DESC", nativeQuery = true)
    List<Household> findAllByArchivedOrderByIdDesc(int archived, List<Long> organisationUnitIds);

    List<Household> findByCboProjectIdAndArchivedOrderByIdDesc(Long cboProjectId, int archived);

    Optional<Household> findByIdAndCboProjectIdAndArchived(Long id, Long cboProjectId, int archived);


}

