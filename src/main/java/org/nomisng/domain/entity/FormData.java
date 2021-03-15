package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "form_data", schema = "public", catalog = "nomis")
public class FormData {
    private Long id;
    private Long encounterId;
    private Object data;
    private Long organisationalUnitId;
    private Encounter encounterByEncounterId;
    private OrganisationUnit organisationUnitByOrganisationalUnitId;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "encounter_id")
    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    @Basic
    @Column(name = "data")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Basic
    @Column(name = "organisational_unit_id")
    public Long getOrganisationalUnitId() {
        return organisationalUnitId;
    }

    public void setOrganisationalUnitId(Long organisationalUnitId) {
        this.organisationalUnitId = organisationalUnitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormData formData = (FormData) o;
        return Objects.equals(id, formData.id) &&
                Objects.equals(encounterId, formData.encounterId) &&
                Objects.equals(data, formData.data) &&
                Objects.equals(organisationalUnitId, formData.organisationalUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, encounterId, data, organisationalUnitId);
    }

    @ManyToOne
    @JoinColumn(name = "encounter_id", referencedColumnName = "id", nullable = false)
    public Encounter getEncounterByEncounterId() {
        return encounterByEncounterId;
    }

    public void setEncounterByEncounterId(Encounter encounterByEncounterId) {
        this.encounterByEncounterId = encounterByEncounterId;
    }

    @ManyToOne
    @JoinColumn(name = "organisational_unit_id", referencedColumnName = "id", nullable = false)
    public OrganisationUnit getOrganisationUnitByOrganisationalUnitId() {
        return organisationUnitByOrganisationalUnitId;
    }

    public void setOrganisationUnitByOrganisationalUnitId(OrganisationUnit organisationUnitByOrganisationalUnitId) {
        this.organisationUnitByOrganisationalUnitId = organisationUnitByOrganisationalUnitId;
    }
}
