package com.example.chocottomemolocal.memo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.chocottomemolocal.memo.entity.FolderEntity;
import com.example.chocottomemolocal.memo.query.FolderMemoRow;
import com.example.chocottomemolocal.memo.repository.FolderRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    public List<FolderEntity> findAll() {
        return folderRepository.findAll();
    }

    private FolderEntity findById(long id) {
        return folderRepository.findById(id);
    }
    
    public List<FolderMemoRow> findMemosById(long id) {
        return folderRepository.findMemosById(id);
    }

    public long create(@NonNull FolderEntity folder) {
        folderRepository.save(folder);
        return folder.getId();
    }

    public FolderEntity update(@NonNull FolderEntity folder) {
        int updatedCount = folderRepository.update(folder);
        if (updatedCount == 0) {
            return null;
        }
        return findById(folder.getId());
    }

    public boolean delete(long id, long updateCount) {
        int deletedCount = folderRepository.delete(id, updateCount);
        return deletedCount > 0;
    }
}
