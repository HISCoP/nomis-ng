package org.nomisng.repository;

import org.nomisng.domain.entity.MemberFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberFlagRepository extends JpaRepository<MemberFlag, Long> {

    Optional<MemberFlag> findByMemberIdAndFlagId(Long memberId, Long flagId);

    List<MemberFlag> findAllByMemberId(Long patientId);

    void deleteByFlagId(Long flagId);
}