package org.nomisng.repository;

import org.nomisng.domain.entity.HouseholdAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdAddressRepository extends JpaRepository<HouseholdAddress, Long>, JpaSpecificationExecutor {

}

