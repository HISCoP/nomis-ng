package org.nomisng.repository;

import org.nomisng.domain.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long>, JpaSpecificationExecutor {

    Optional<Household> findByIdAndArchived(Long id, int archived);

    List<Household> findAllByArchived(int archived);
}

