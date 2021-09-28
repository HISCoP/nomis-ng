package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "cbo_donor_ip_organisation_unit")
public class CboDonorIpOrganisationUnit {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "cbo_id")
    private Long cboId;

    @Basic
    @Column(name = "donor_id")
    private Long donorId;

    @Basic
    @Column(name = "ip_id")
    private Long ipId;

    @Basic
    @Column(name = "organisation_unit_id")
    private Long organisationUnitId;

    @ManyToOne
    @JoinColumn(name = "cbo_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Cbo cboByCboId;

    @ManyToOne
    @JoinColumn(name = "donor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Donor donorByDonorId;

    @ManyToOne
    @JoinColumn(name = "ip_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ip ipByIpId;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganisationUnit organisationUnitByOrganisationUnitId;
}
