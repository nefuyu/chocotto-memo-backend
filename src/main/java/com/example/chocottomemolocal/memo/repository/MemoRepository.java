package com.example.chocottomemolocal.memo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.chocottomemolocal.memo.entity.MemoEntity;
import com.example.chocottomemolocal.memo.mapper.MemoMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemoRepository {
    private final MemoMapper memoMapper;

    public List<MemoEntity> findAll() {
        return memoMapper.findAll();
    }

    public MemoEntity findById(long id) {
        return memoMapper.findById(id);
    }

    public long save(MemoEntity memo) {
        return memoMapper.save(memo);
    }

    public int update(MemoEntity memo) {
        return memoMapper.update(memo);
    }

    public int delete(long id, long updateCount) {
        return memoMapper.delete(id, updateCount);
    }
}
