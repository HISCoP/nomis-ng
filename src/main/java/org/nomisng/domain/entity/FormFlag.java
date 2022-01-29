package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "form_flag")
public class FormFlag extends Audit implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "status", nullable = false)
    private Integer status; // 0 is associated with, 1 is applied to

    @Basic
    @Column(name = "form_code", nullable = false)
    private String formCode;

    @Basic
    @Column(name = "flag_id")
    private Long flagId;

    @Basic
    @JsonIgnore
    @Column(name = "archived")
    private Integer archived=0;

    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "form_code", referencedColumnName = "code", insertable = false, updatable = false)
    private Form formByFormId;

    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "flag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Flag flag;
}
