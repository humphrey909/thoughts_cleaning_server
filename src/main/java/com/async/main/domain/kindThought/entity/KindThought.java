package com.async.main.domain.kindThought.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kind_thought_list")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KindThought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "detail_text", columnDefinition = "TEXT")
    private String detailText;
}

