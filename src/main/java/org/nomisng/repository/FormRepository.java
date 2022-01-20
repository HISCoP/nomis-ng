package org.nomisng.repository;

import org.nomisng.domain.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FormRepository extends JpaRepository<Form, Long>, JpaSpecificationExecutor {
    Optional<Form> findByCodeAndArchived(String code, int archived);

    Optional<Form> findByIdAndArchived(Long id, int archived);

    List<Form> findAllByArchivedOrderByIdAsc(int archived);

    Optional<Form> findByNameAndArchived(String name, int archived);

    List<Form> findAllByArchivedAndFormTypeOrderByIdAsc(int archived, Integer formType);

    /*@Query(value = "SELECT code FROM form", nativeQuery = true)
    List<String> findAllPermissionCode();*/

    @Query(value = "SELECT * FROM form WHERE archived=?1 " +
            "AND form_type IN ?2", nativeQuery = true)
    List<Form> findAllByArchivedAndFormTypeOrderByIdAsc(int archived, List<Integer> formType);
}

