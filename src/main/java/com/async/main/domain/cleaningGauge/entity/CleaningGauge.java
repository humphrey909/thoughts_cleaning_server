package com.async.main.domain.cleaningGauge.entity;

import com.async.main.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cleaning_gauge")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleaningGauge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "thought_idx", nullable = false)
    private Long thoughtIdx;

    @Column(name = "space_type")
    private String spaceType;

    @Column(name = "components_type")
    private String componentsType;

    @Column(name = "clean_gauge")
    private Float cleanGauge;
}

