package org.nomisng.domain.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Encounter {
    private Long id;
    private Date dateEncounter;
    private Long serviceId;
    private Long organisationalUnitId;
    private String uuid;
    private Collection<FormData> formDataById;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_encounter")
    public Date getDateEncounter() {
        return dateEncounter;
    }

    public void setDateEncounter(Date dateEncounter) {
        this.dateEncounter = dateEncounter;
    }

    @Basic
    @Column(name = "service_id")
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "organisational_unit_id")
    public Long getOrganisationalUnitId() {
        return organisationalUnitId;
    }

    public void setOrganisationalUnitId(Long organisationalUnitId) {
        this.organisationalUnitId = organisationalUnitId;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encounter encounter = (Encounter) o;
        return Objects.equals(id, encounter.id) &&
                Objects.equals(dateEncounter, encounter.dateEncounter) &&
                Objects.equals(serviceId, encounter.serviceId) &&
                Objects.equals(organisationalUnitId, encounter.organisationalUnitId) &&
                Objects.equals(uuid, encounter.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateEncounter, serviceId, organisationalUnitId, uuid);
    }

    @OneToMany(mappedBy = "encounterByEncounterId")
    public Collection<FormData> getFormDataById() {
        return formDataById;
    }

    public void setFormDataById(Collection<FormData> formDataById) {
        this.formDataById = formDataById;
    }
}
