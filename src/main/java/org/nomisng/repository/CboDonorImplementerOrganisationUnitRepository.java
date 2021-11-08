package org.nomisng.repository;

import org.nomisng.domain.entity.CboDonorImplementerOrganisationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CboDonorImplementerOrganisationUnitRepository extends JpaRepository<CboDonorImplementerOrganisationUnit, Long>, JpaSpecificationExecutor {

}

