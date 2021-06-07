package org.nomisng.repository;

import org.nomisng.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor {
    String findByCode(String Code);

    Optional<Program> findProgramByCodeAndArchived(String Code, int archived);


    Optional<Program> findByIdAndArchived(Long id, int archived);

    List<Program> findAllByArchivedIsNotOrderByIdDesc(int archived);
}

