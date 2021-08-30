package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "ip")
public class Ip extends Audit{

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "archived")
    private Integer archived = 0;

    @OneToMany(mappedBy = "ipByIpId")
    private List<CboDonorIpOrganisationUnit> cboDonorIpOrganisationUnitsById;
}
