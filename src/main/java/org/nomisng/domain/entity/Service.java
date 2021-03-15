package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Service {
    private Long id;
    private String name;
    private Long serviceTypeId;
    private Long domainId;
    private Collection<Form> formsById;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "service_type_id")
    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Basic
    @Column(name = "domain_id")
    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id) &&
                Objects.equals(name, service.name) &&
                Objects.equals(serviceTypeId, service.serviceTypeId) &&
                Objects.equals(domainId, service.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, serviceTypeId, domainId);
    }

    @OneToMany(mappedBy = "serviceByServiceId")
    public Collection<Form> getFormsById() {
        return formsById;
    }

    public void setFormsById(Collection<Form> formsById) {
        this.formsById = formsById;
    }
}
