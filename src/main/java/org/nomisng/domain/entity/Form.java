package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "form")
public class Form extends JsonBEntity implements Serializable {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    @NotNull(message = "name cannot be null")
    private String name;

    @Basic
    @Column(name = "code")
    private String code;

    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "resource_object", nullable = false, columnDefinition = "jsonb")
    private Object resourceObject;

    @Basic
    @Column(name = "resource_path")
    private String resourcePath;

    /*@Basic
    @Column(name = "form_type")
    private String formType;*/

    @Basic
    @Column(name = "version")
    private String version;

    @Basic
    @Column(name = "archived")
    private Integer archived = 0;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private String createdBy = "guest@nomisng.org";

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateCreated = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonIgnore
    @ToString.Exclude
    private String modifiedBy = "guest@nomisng.org";

    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime dateModified = LocalDateTime.now();

    @Basic
    @Column(name = "support_services")
    private String supportServices;

    /*@ManyToOne
    @JoinColumn(name = "service_code", referencedColumnName = "code", updatable = false, insertable = false)
    @JsonIgnore
    private OvcService ovcServiceByOvcServiceCode;*/

    /*@Transient
    private String ovcServiceName;

    public String getOvcServiceName(){
        if(ovcServiceByOvcServiceCode != null){
            return ovcServiceByOvcServiceCode.getName();
        }
        return null;
    }

    @PrePersist
    public void update() {
        if(this.code == null || this.code.isEmpty()) {
            this.code = UUID.randomUUID().toString();
        }
    }*/

    @OneToMany(mappedBy = "formByFormCode")
    @JsonIgnore
    @ToString.Exclude
    private List<Encounter> FormByFormCode;
}
