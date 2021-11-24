package org.nomisng.repository;

import org.nomisng.domain.entity.HouseholdMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<HouseholdMember> findAllByCboProjectIdAndArchivedOrderByIdDesc(Long cboProjectId, int archived, Pageable pageable);

    @Query(value = "SELECT * FROM household_member h " +
            "WHERE (h.details ->>'firstName' ilike ?1  " +
            "OR h.details ->>'lastName' ilike ?1 " +
            "OR h.details ->>'mobilePhoneNumber' ilike ?1) " +
            "AND cbo_project_id = ?2 AND h.archived=?3", nativeQuery = true)
    Page<HouseholdMember> findAllByCboProjectIdAndArchivedAndSearchParameterOrderByIdDesc(String search, Long cboProjectId, int archived, Pageable pageable);

    @Query(value = "SELECT Count(*) FROM household_member WHERE household_id = ?1", nativeQuery = true)
    Long findHouseholdMemberCountOfHousehold(Long householdId);


}

