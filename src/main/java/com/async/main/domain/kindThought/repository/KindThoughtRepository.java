package com.async.main.domain.kindThought.repository;

import com.async.main.domain.kindThought.entity.KindThought;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindThoughtRepository extends JpaRepository<KindThought, Long> {
}

