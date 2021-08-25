package org.nomisng.repository;

import org.nomisng.domain.entity.HouseholdMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdMemberRepository extends JpaRepository<HouseholdMember, Long>, JpaSpecificationExecutor {

    Optional<HouseholdMember> findByIdAndArchived(Long id, int archived);

    @Query(value = "SELECT * FROM household_member h WHERE " +
            "h.household_id=?1 AND h.details ->>'firstName' ilike ?2  " +
            "AND h.details ->>'lastName' ilike ?3 AND h.archived=?4", nativeQuery = true)
    Optional<HouseholdMember> findByHouseholdIdAndFirstNameAndLastNameAndArchived(Long householdId, String firstName, String lastName, int archived);

    List<HouseholdMember> findAllByArchivedOrderByIdDesc(int archived);
}

