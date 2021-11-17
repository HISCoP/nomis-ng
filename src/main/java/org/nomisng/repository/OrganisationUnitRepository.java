package org.nomisng.repository;

import org.nomisng.domain.entity.OrganisationUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganisationUnitRepository extends JpaRepository<OrganisationUnit, Long> {
    List<OrganisationUnit> findAllOrganisationUnitByParentOrganisationUnitIdAndArchived(Long id, int archived);

    List<OrganisationUnit> findAllByParentOrganisationUnitIdAndOrganisationUnitLevelId(Long parentOrgUnitId, Long orgUnitLevelId);

    //List<OrganisationUnit> findAllByOrganisationUnitLevelId(Long id, Pageable pageable);

    Optional<OrganisationUnit> findByNameAndParentOrganisationUnitIdAndArchived(String name, Long parentOrganisationUnitId, int archived);

    Optional<OrganisationUnit> findByIdAndArchived(Long id, int archived);

    List<OrganisationUnit> findAllByArchivedOrderByIdAsc(int unarchived);

    Page<OrganisationUnit> findAllByOrganisationUnitLevelId(Long organisationUnitLevelId, Pageable pageable);

    @Query(value = "SELECT id from organisation_unit WHERE name ilike ?1" +
            " AND description ilike '%local%'AND " +
            "parent_organisation_unit_id = (SELECT id from organisation_unit WHERE name = ?2 " +
            "AND organisation_unit_level_id=2)", nativeQuery = true)
    Long findByOrganisationDetails(String parentOrganisationUnitName, String parentsParentOrganisationUnitName);

    Optional<OrganisationUnit> findByNameAndArchived(String organisationUnitName, int archived);


    @Query(value = "SELECT id from organisation_unit WHERE parent_organisation_unit_id = ?1 " +
            "AND archived = 0", nativeQuery = true)
    List<Long> findAllOrganisationUnitIdByParentOrganisationUnitId(Long parentOrganisationUnitId);

    @Query(value = "SELECT * from organisation_unit WHERE parent_organisation_unit_id = ?1 " +
            "AND archived = 0 LIMIT 1", nativeQuery = true)
    OrganisationUnit findOneOrganisationUnitByParentOrganisationUnitId(Long parentOrganisationUnitId);

    @Query(value = "SELECT id FROM organisation_unit WHERE archived=0 AND organisation_unit_level_id=4", nativeQuery = true)
    List<Long> findAllId();

    @Query(value = "SELECT * from organisation_unit WHERE organisation_unit_level_id = ?1" +
            " AND name ilike ?2", nativeQuery = true)
    Page<OrganisationUnit> findAllByOrganisationByLevelAndName(Long organisationUnitLevelId, String organisationUnitName, Pageable pageable);

    @Query(value = "SELECT * FROM organisation_unit WHERE id " +
            "IN (SELECT organisation_unit_id FROM cbo_project_location WHERE cbo_project_id=?1)", nativeQuery = true)
    List<OrganisationUnit> findAllByCboProjectIdId(Long cboProjectId);

}
