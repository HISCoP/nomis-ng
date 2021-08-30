package org.nomisng.repository;

import org.nomisng.domain.entity.Cbo;
import org.nomisng.domain.entity.CboDonorIpOrganisationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CboDonorIpOrganisationUnitRepository extends JpaRepository<CboDonorIpOrganisationUnit, Long>, JpaSpecificationExecutor {

}

