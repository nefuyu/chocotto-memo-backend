package com.example.chocottomemolocal.memo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.chocottomemolocal.memo.entity.MemoEntity;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemosResponse {
    private List<MemoDto> memos;

    public static MemosResponse from(List<MemoEntity> entities) {
        return MemosResponse.builder()
            .memos(entities.stream().map(MemoDto::from).toList())
            .build();
    }
}