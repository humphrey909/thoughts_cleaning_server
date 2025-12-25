package com.async.main.domain.kindThought.controller;

import com.async.main.base.ROAny;
import com.async.main.domain.kindThought.dto.KindThoughtDto;
import com.async.main.domain.kindThought.service.KindThoughtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kind-thought")
@RequiredArgsConstructor
public class KindThoughtController {

    private final KindThoughtService kindThoughtService;

    @GetMapping("/list")
    public ResponseEntity<ROAny> getList() {
        List<KindThoughtDto> list = kindThoughtService.findAll();
        
        ROAny response = new ROAny();
        response.put("kind_thought_list", list);
        return ResponseEntity.ok(response);
        
    }
}

