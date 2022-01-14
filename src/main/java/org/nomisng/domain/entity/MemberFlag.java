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
@Table(name = "member_flag")
public class MemberFlag implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "member_id")
    private Long memberId;

    @Basic
    @Column(name = "flag_id")
    private Long flagId;

    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "member_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HouseholdMember memberByMemberId;

    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "flag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Flag flag;

}