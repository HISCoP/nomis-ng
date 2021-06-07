package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "encounter")
public class Encounter extends Audit{
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "date_encounter")
    private Date dateEncounter;

    @Basic
    @Column(name = "form_code")
    private String formCode;

    @Basic
    @Column(name = "service_code")
    private String serviceCode;

    @Basic
    @Column(name = "organisational_unit_id")
    private Long organisationUnitId;

    @OneToMany(mappedBy = "encounterByEncounterId")
    public List<FormData> getFormDataById;

    @ManyToOne
    @JoinColumn(name = "service_code", referencedColumnName = "code", updatable = false, insertable = false)
    @JsonIgnore
    private Program programByProgramCode;
}
