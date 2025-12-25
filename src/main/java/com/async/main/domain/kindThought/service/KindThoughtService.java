package com.async.main.domain.kindThought.service;

import com.async.main.domain.kindThought.dto.KindThoughtDto;
import com.async.main.domain.kindThought.repository.KindThoughtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KindThoughtService {

    private final KindThoughtRepository kindThoughtRepository;

    public List<KindThoughtDto> findAll() {
        return kindThoughtRepository.findAll().stream()
                .map(KindThoughtDto::from)
                .collect(Collectors.toList());
    }
}

