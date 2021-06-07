package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "service")
public class Program extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "code", updatable = false, unique = true)
    private String code;

    @Basic
    @Column(name = "domain_id")
    private Long domainId;

    @Basic
    @Column(name = "archived")
    private Integer archived;


    @JsonIgnore
    @OneToMany(mappedBy = "programByProgramCode")
    private List<Form> formByProgramCode;

    @JsonIgnore
    @OneToMany(mappedBy = "programByProgramCode")
    private List<Encounter> encounterByProgramCode;

    @ManyToOne
    @JoinColumn(name = "domain_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Domain domainByDomainId;

    @PrePersist
    public void update() {
        this.code = UUID.randomUUID().toString();
    }
}
