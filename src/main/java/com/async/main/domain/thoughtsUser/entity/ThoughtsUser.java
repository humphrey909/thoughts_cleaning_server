package com.async.main.domain.thoughtsUser.entity;

import com.async.main.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "thoughts_user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThoughtsUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "uid", nullable = false)
    private Integer uid;

    @Column(name = "kind_thought_idx")
    private Integer kindThoughtIdx;

    @Column(name = "content_thought", columnDefinition = "TEXT")
    private String contentThought;
}

