package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "application_codeset")
public class ApplicationCodeset extends Audit<String> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "codeset_group")
    private String codesetGroup;

    @Basic
    @Column(name = "display")
    private String display;

    @Basic
    @Column(name = "language")
    private String language;

    @Basic
    @Column(name = "version")
    private String version;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "archived")
    private Integer archived;

    @Basic
    @Column(name = "active")
    public Integer active;

    @OneToMany(mappedBy = "applicationCodesetByApplicationCodesetId")
    @ToString.Exclude
    public Collection<ApplicationCodesetStandardCodeset> applicationCodesetStandardCodesetsById;


    @OneToMany(mappedBy = "applicationCodesetByFormTypeId")
    @ToString.Exclude
    public Collection<Form> formsById;
}
