package org.nomisng.repository;

import org.nomisng.domain.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long>, JpaSpecificationExecutor {

    List<Encounter> findAllByArchived(int unArchived);

    Optional<Encounter> findByIdAndArchived(Long id, int archived);

    @Query(value = "SELECT * FROM encounter e " +
            "LEFT JOIN household h ON h.id = e.household_member_id " +
            "WHERE h.id =?1 AND h.archived=?2 AND e.archived=?3 ORDER BY e.id DESC;", nativeQuery = true)
    List<Encounter> findAllHouseholdMemberAndArchived(Long householdId, int unArchivedHouseHold, int unArchivedEncounter);
}

