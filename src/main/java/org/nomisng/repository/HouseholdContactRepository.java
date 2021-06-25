package org.nomisng.repository;

import org.nomisng.domain.entity.HouseholdContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdContactRepository extends JpaRepository<HouseholdContact, Long>, JpaSpecificationExecutor {

}

