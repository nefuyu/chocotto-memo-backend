package com.example.chocottomemolocal.memo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chocottomemolocal.memo.response.FolderMemosResponse;
import com.example.chocottomemolocal.memo.response.FolderResponse;
import com.example.chocottomemolocal.memo.response.FoldersResponse;
import com.example.chocottomemolocal.memo.service.FolderService;

import jakarta.validation.Valid;

import com.example.chocottomemolocal.memo.entity.FolderEntity;
import com.example.chocottomemolocal.memo.query.FolderMemoRow;
import com.example.chocottomemolocal.memo.request.FolderCreateRequest;
import com.example.chocottomemolocal.memo.request.FolderUpdateRequest;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @GetMapping()
    public ResponseEntity<FoldersResponse> findAll() {
        return ResponseEntity.ok(FoldersResponse.from(folderService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderMemosResponse> findMemosById(@PathVariable Long id) {
        final List<FolderMemoRow> rows = folderService.findMemosById(id);
        if (rows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(FolderMemosResponse.from(rows));
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody @Valid FolderCreateRequest request) {
        final long id = folderService.create(FolderEntity.from(request));
        return ResponseEntity.created(URI.create("/api/folders/" + id)).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FolderResponse> update(@PathVariable Long id, @RequestBody @Valid FolderUpdateRequest request) {
        final FolderEntity folder = folderService.update(FolderEntity.from(id, request));
        if (folder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(FolderResponse.from(folder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam long updateCount) {
        boolean deleted = folderService.delete(id, updateCount);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
