package com.example.chocottomemolocal.memo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chocottomemolocal.memo.entity.MemoEntity;
import com.example.chocottomemolocal.memo.request.MemoCreateRequest;
import com.example.chocottomemolocal.memo.request.MemoDeleteRequest;
import com.example.chocottomemolocal.memo.request.MemoUpdateRequest;
import com.example.chocottomemolocal.memo.response.MemoResponse;
import com.example.chocottomemolocal.memo.response.MemosResponse;
import com.example.chocottomemolocal.memo.service.MemoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService;

    @GetMapping()
    public ResponseEntity<MemosResponse> getAll() {
        final List<MemoEntity> memos = memoService.findAll();
        return ResponseEntity.ok(MemosResponse.from(memos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponse> getById(@PathVariable Long id) {
        final MemoEntity memo = memoService.findById(id);
        if (memo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemoResponse.from(memo));
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody @Valid MemoCreateRequest request) {
        final long id = memoService.create(MemoEntity.from(request));
        return ResponseEntity.created(URI.create("/api/memos/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponse> update(@PathVariable Long id,
        @RequestBody @Valid MemoUpdateRequest request) {
        final MemoEntity memo = memoService.update(MemoEntity.from(id, request));
        if (memo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemoResponse.from(memo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
        @RequestBody @Valid MemoDeleteRequest request) {
        boolean deleted = memoService.delete(id, request.getUpdateCount());
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
