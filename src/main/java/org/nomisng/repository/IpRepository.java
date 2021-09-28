package org.nomisng.repository;

import org.nomisng.domain.entity.Donor;
import org.nomisng.domain.entity.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IpRepository extends JpaRepository<Ip, Long>, JpaSpecificationExecutor {

}

