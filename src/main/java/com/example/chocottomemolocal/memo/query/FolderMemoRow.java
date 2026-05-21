package com.example.chocottomemolocal.memo.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FolderMemoRow {
    private long folderId;
    private String folderName;
    private long folderCreatedAt;
    private long folderUpdatedAt;
    private Long memoId;
    private String memoTitle;
    private Long memoCreatedAt;
    private Long memoUpdatedAt;
}
