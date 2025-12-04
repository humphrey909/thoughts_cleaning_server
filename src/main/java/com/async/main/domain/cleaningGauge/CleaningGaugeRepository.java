package com.async.main.domain.cleaningGauge;

import com.async.main.domain.cleaningGauge.entity.CleaningGauge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleaningGaugeRepository extends JpaRepository<CleaningGauge, Long> {
}

