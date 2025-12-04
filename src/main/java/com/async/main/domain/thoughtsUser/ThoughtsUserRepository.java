package com.async.main.domain.thoughtsUser;

import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThoughtsUserRepository extends JpaRepository<ThoughtsUser, Long> {
}
