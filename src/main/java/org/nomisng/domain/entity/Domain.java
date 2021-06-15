package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "domain")
public class Domain extends Audit {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "code", updatable = false)
    private String code;

    @Basic
    @Column(name = "archived", updatable = false)
    private int archived;

    @OneToMany(mappedBy = "domainByDomainId")
    @JsonIgnore
    public Collection<Program> servicesById;
}
