package com.example.chocottomemolocal.memo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.chocottomemolocal.memo.entity.MemoEntity;
import com.example.chocottomemolocal.memo.repository.MemoRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    public List<MemoEntity> findAll() {
        return memoRepository.findAll();
    }

    public MemoEntity findById(Long id) {
        return memoRepository.findById(id);
    }


    public long create(@NonNull MemoEntity memo) {
        memoRepository.save(memo);
        return memo.getId();
    }

    public MemoEntity update(@NonNull MemoEntity memo) {
        int updatedCount = memoRepository.update(memo);
        if (updatedCount == 0) {
            return null;
        }
        return findById(memo.getId());
    }

    public boolean delete(long id, long updateCount) {
        int deletedCount = memoRepository.delete(id, updateCount);
        return deletedCount > 0;
    }
}
