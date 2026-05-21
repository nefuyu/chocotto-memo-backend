package com.example.chocottomemolocal.memo.response;

import com.example.chocottomemolocal.memo.entity.FolderEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FolderResponse {
    private long folderId;
    private String name;
    private long createdAt;
    private long updatedAt;

    public static FolderResponse from(@NonNull FolderEntity entity) {
        return FolderResponse.builder()
                .folderId(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
