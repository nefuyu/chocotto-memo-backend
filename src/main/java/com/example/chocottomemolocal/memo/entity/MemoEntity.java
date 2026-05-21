package com.example.chocottomemolocal.memo.entity;

import com.example.chocottomemolocal.memo.request.MemoCreateRequest;
import com.example.chocottomemolocal.memo.request.MemoUpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoEntity {
    private long id;
    private String title;
    private String content;
    private boolean isDeleted;
    private long createdAt;
    private long updatedAt;
    private long updateCount;

    public static MemoEntity from(@NonNull MemoCreateRequest request) {
        MemoEntity memo = MemoEntity
            .builder()
            .title(request.getTitle())
            .content(request.getContent())
            .build();
        return memo;
    }

    public static MemoEntity from(@NonNull Long id, @NonNull MemoUpdateRequest request) {
        MemoEntity memo = MemoEntity
            .builder()
            .id(id)
            .title(request.getTitle())
            .content(request.getContent())
            .updateCount(request.getUpdateCount())
            .build();
        return memo;
    }
}
