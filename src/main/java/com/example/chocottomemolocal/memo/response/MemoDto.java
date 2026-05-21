package com.example.chocottomemolocal.memo.response;

import com.example.chocottomemolocal.memo.entity.MemoEntity;
import com.example.chocottomemolocal.memo.query.FolderMemoRow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoDto {
    private Long id;
    private String title;
    private long createdAt;
    private long updatedAt;

    public static MemoDto from(MemoEntity entity) {
        return MemoDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static MemoDto from(FolderMemoRow row) {
        return MemoDto.builder()
                .id(row.getMemoId())
                .title(row.getMemoTitle())
                .createdAt(row.getMemoCreatedAt())
                .updatedAt(row.getMemoUpdatedAt())
                .build();
    }
}
