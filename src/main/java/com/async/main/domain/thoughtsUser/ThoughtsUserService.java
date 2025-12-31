package com.async.main.domain.thoughtsUser;

import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import com.async.main.domain.thoughtsUser.dto.ThoughtsUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThoughtsUserService {

    private final ThoughtsUserRepository thoughtsUserRepository;

    @Transactional
    public ThoughtsUser save(ThoughtsUserDto dto) {
        return thoughtsUserRepository.save(dto.toEntity());
    }
}
