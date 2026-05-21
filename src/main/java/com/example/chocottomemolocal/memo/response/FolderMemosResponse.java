package com.example.chocottomemolocal.memo.response;

import java.util.List;

import com.example.chocottomemolocal.memo.query.FolderMemoRow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FolderMemosResponse {
    private long folderId;
    private String name;
    private long createdAt;
    private long updatedAt;
    private List<MemoDto> memos;
    
    public static FolderMemosResponse from(@NonNull List<FolderMemoRow> rows) {
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("rows must not be empty");
        }

        final FolderMemoRow folder = rows.get(0);
        return FolderMemosResponse.builder()
                .folderId(folder.getFolderId())
                .name(folder.getFolderName())
                .createdAt(folder.getFolderCreatedAt())
                .updatedAt(folder.getFolderUpdatedAt())
                .memos(rows.stream().filter(row -> row.getMemoId() != null).map(MemoDto::from).toList())
                .build();
    }
}
