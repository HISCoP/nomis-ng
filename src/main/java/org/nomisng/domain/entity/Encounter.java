package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

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
    private Long serviceCode;

    @Basic
    @Column(name = "organisational_unit_id")
    private Long organisationalUnitId;

    @OneToMany(mappedBy = "encounterByEncounterId")
    public Collection<FormData> getFormDataById;
}
