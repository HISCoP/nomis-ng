package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "flag")
public class Flag extends Audit implements Serializable {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "field_name")
    private String fieldName;

    @Basic
    @Column(name = "type")
    private Integer type;

    @Basic
    @Column(name = "field_value")
    private String fieldValue;

    @Basic
    @Column(name = "datatype")
    private Integer datatype; // 0 - string, 1 - application codeset, 2 - integer, 3 - form level flag

    @Basic
    @Column(name = "operator")
    private String operator;

    @Basic
    @Column(name = "continuous")
    private Boolean continuous = false;

    @Basic
    @Column(name = "archived")
    @JsonIgnore
    private Integer archived=0;

    @OneToMany(mappedBy = "flag")
    @JsonIgnore
    @ToStringExclude
    private List<MemberFlag> memberFlagsById;

    @OneToMany(mappedBy = "flag")
    @JsonIgnore
    @ToStringExclude
    private List<FormFlag> formsByIdFlag;
}