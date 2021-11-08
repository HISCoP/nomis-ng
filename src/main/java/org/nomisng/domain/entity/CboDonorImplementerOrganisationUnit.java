package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "cbo_donor_implementer_organisation_unit")
public class CboDonorImplementerOrganisationUnit {

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
    @Column(name = "implementer_id")
    private Long implementerId;

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
    @JoinColumn(name = "implementer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Implementer implementerByImplementerId;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganisationUnit organisationUnitByOrganisationUnitId;

    @Transient
    private List<Long> organisationUnitIds;
}
