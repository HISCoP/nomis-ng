package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "application_user_cbo_project")
public class ApplicationUserCboProject extends Audit {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "application_user_id")
    private Long applicationUserId;

    @Basic
    @Column(name = "cbo_project_id")
    private Long cboProjectId;

    @Basic
    @Column(name = "archived")
    @JsonIgnore
    private int archived;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "application_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User applicationUserByApplicationUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CboProject cboProjectByCboProjectId;


    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "application_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User getUserByApplicationUserId;

    public String getCboProjectDescription(){
        if (cboProjectId != null){
            return cboProjectByCboProjectId.getDescription();
        }
        return null;
    }
}
