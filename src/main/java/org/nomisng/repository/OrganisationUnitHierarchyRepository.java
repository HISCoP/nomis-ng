package org.nomisng.repository;

import org.nomisng.domain.entity.OrganisationUnitHierarchy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganisationUnitHierarchyRepository extends JpaRepository<OrganisationUnitHierarchy, Long> {

    Page<OrganisationUnitHierarchy> findAllByParentOrganisationUnitIdAndOrganisationUnitLevelId(Long parent_org_unit_id, Long org_unit_level_id, Pageable pageable);
}
