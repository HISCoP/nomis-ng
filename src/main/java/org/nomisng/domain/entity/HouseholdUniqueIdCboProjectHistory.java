package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "household_unique_id_cbo_project_history")
public class HouseholdUniqueIdCboProjectHistory extends Audit {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "household_id")
    private Long householdId;

    @Basic
    @Column(name = "cbo_project_id")
    private Long cboProjectId;

    @Basic
    @Column(name = "archived")
    private Integer archived;

    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Household householdByHouseholdId;

    @ManyToOne
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CboProject cboProjectByCboProjectId;
}
