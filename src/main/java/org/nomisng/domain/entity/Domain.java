package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "domain")
public class Domain {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "uuid")
    private String uuid;
}
