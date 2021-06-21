package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "encounter")
public class Encounter extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "date_encounter")
    private LocalDateTime dateEncounter;

    @Basic
    @Column(name = "form_code")
    private String formCode;

    @Basic
    @Column(name = "service_code")
    private String ovcServiceCode;

    @Basic
    @Column(name = "household_member_id")
    private Long householdMemberId;

    @Basic
    @Column(name = "organisational_unit_id")
    @JsonIgnore
    private Long organisationUnitId;

    @OneToMany(mappedBy = "encounterByEncounterId")
    @ToStringExclude
    @JsonIgnore
    private List<FormData> formDataById;

    @ManyToOne
    @JoinColumn(name = "service_code", referencedColumnName = "code", updatable = false, insertable = false)
    @JsonIgnore
    @ToStringExclude
    private OvcService ovcServiceByOvcServiceCode;

    @ManyToOne
    @JoinColumn(name = "household_member_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    @ToStringExclude
    private HouseholdMember householdMemberByHouseholdMemberId;
}
