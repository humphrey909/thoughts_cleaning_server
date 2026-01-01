package com.async.main.domain.thoughtsUser;

import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThoughtsUserRepository extends JpaRepository<ThoughtsUser, Integer> {
    List<ThoughtsUser> findAllByUid(Integer uid);
}
