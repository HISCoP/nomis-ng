package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "application_codeset_standard_codeset")
public class ApplicationCodesetStandardCodeset extends Audit<String> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "application_codeset_id")
    private Long applicationCodesetId;

    @Basic
    @Column(name = "standard_codeset_id")
    private Long standardCodesetId;

    @ManyToOne
    @JoinColumn(name = "application_codeset_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    public ApplicationCodeset applicationCodesetByApplicationCodesetId;

    @ManyToOne
    @JoinColumn(name = "standard_codeset_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    public StandardCodeset standardCodesetByStandardCodesetId;
}
