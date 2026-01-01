package com.async.main.domain.thoughtsUser;

import com.async.main.domain.thoughtsUser.entity.ThoughtsUser;
import com.async.main.domain.thoughtsUser.dto.ThoughtsUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThoughtsUserService {

    private final ThoughtsUserRepository thoughtsUserRepository;

    @Transactional
    public ThoughtsUser save(ThoughtsUserDto dto) {
        return thoughtsUserRepository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<ThoughtsUser> findAllByUid(Integer uid) {
        return thoughtsUserRepository.findAllByUid(uid);
    }
}
