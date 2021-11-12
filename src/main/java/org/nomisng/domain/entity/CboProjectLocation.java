package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "cbo_project_location")
@AllArgsConstructor
@NoArgsConstructor
public class CboProjectLocation {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "cbo_project_id")
    private Long cboProjectId;

    @Basic
    @Column(name = "organisation_unit_id")
    private Long organisationUnitId;

    @ManyToOne
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    @ToString.Exclude
    private CboProject cboProjectByCboProjectId;

    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    @ToString.Exclude
    private OrganisationUnit organisationUnitByOrganisationUnitId;
}
