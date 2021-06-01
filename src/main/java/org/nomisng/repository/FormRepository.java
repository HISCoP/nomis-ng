package org.nomisng.repository;

import org.nomisng.domain.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long>, JpaSpecificationExecutor {
    Optional<Form> findByCodeAndArchived(String code, int archived);

    Optional<Form> findByIdAndArchived(Long id, int archived);

    List<Form> findAllByArchivedOrderByIdAsc(int archived);

    Optional<Form> findByNameAndServiceIdAndArchived(String name, Long serviceId, int archived);
}

