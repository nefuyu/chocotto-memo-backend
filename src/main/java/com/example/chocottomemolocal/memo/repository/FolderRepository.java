package com.example.chocottomemolocal.memo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.chocottomemolocal.memo.entity.FolderEntity;
import com.example.chocottomemolocal.memo.mapper.FolderMapper;
import com.example.chocottomemolocal.memo.query.FolderMemoRow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FolderRepository {
    private final FolderMapper folderMapper;

    public List<FolderEntity> findAll() {
        return folderMapper.findAll();
    }

    public FolderEntity findById(long id) {
        return folderMapper.findById(id);
    }
    
    public List<FolderMemoRow> findMemosById(long id) {
        return folderMapper.findMemosById(id);
    }

    public long save(@NonNull FolderEntity folder) {
        folderMapper.save(folder);
        return folder.getId();
    }

    public int update(@NonNull FolderEntity folder) {
        return folderMapper.update(folder);
    }

    public int delete(long id, long updateCount) {
        return folderMapper.delete(id, updateCount);
    }
}
