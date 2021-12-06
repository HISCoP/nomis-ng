package org.nomisng.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "visit")
public class Visit extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Basic
    @Column(name = "end_time")
    private LocalDateTime endTime;
}
