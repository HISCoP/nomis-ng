package org.nomisng.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Form {
    private Long id;
    private String name;
    private String code;
    private Object resourceObject;
    private Long formTypeId;
    private Long serviceId;
    private ApplicationCodeset applicationCodesetByFormTypeId;
    private Service serviceByServiceId;

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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "resource_object")
    public Object getResourceObject() {
        return resourceObject;
    }

    public void setResourceObject(Object resourceObject) {
        this.resourceObject = resourceObject;
    }

    @Basic
    @Column(name = "form_type_id")
    public Long getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(Long formTypeId) {
        this.formTypeId = formTypeId;
    }

    @Basic
    @Column(name = "service_id")
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Form form = (Form) o;
        return Objects.equals(id, form.id) &&
                Objects.equals(name, form.name) &&
                Objects.equals(code, form.code) &&
                Objects.equals(resourceObject, form.resourceObject) &&
                Objects.equals(formTypeId, form.formTypeId) &&
                Objects.equals(serviceId, form.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, resourceObject, formTypeId, serviceId);
    }

    @ManyToOne
    @JoinColumn(name = "form_type_id", referencedColumnName = "id")
    public ApplicationCodeset getApplicationCodesetByFormTypeId() {
        return applicationCodesetByFormTypeId;
    }

    public void setApplicationCodesetByFormTypeId(ApplicationCodeset applicationCodesetByFormTypeId) {
        this.applicationCodesetByFormTypeId = applicationCodesetByFormTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    public Service getServiceByServiceId() {
        return serviceByServiceId;
    }

    public void setServiceByServiceId(Service serviceByServiceId) {
        this.serviceByServiceId = serviceByServiceId;
    }
}
