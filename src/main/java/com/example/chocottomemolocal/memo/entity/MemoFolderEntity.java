package com.example.chocottomemolocal.memo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoFolderEntity {
    private long memoId;
    private long folderId;
    private long memoPosition;
    private long createdAt;
    private long updatedAt;
    private long updateCount;
}
