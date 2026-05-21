package com.example.chocottomemolocal.memo.response;

import com.example.chocottomemolocal.memo.entity.MemoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemoResponse {
    private long id;
    private String title;
    private String content;
    private long createdAt;
    private long updatedAt;
    private long updateCount;

    public static MemoResponse from(MemoEntity entity) {
        return MemoResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .updateCount(entity.getUpdateCount())
                .build();
    }
}
