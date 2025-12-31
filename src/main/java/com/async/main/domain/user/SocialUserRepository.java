package com.async.main.domain.user;

import com.async.main.domain.user.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    Optional<SocialUser> findByUserIdAndLoginCompany(Long userId, String loginCompany);
    List<SocialUser> findAllByUserId(Long userId);
}
