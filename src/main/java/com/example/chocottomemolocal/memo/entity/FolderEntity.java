package com.example.chocottomemolocal.memo.entity;

import com.example.chocottomemolocal.memo.request.FolderCreateRequest;
import com.example.chocottomemolocal.memo.request.FolderUpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FolderEntity {
    private long id;
    private String name;
    private long createdAt;
    private long updatedAt;
    private long updateCount;

    public static FolderEntity from(@NonNull FolderCreateRequest request) {
        FolderEntity folder = FolderEntity
            .builder()
            .name(request.getName())
            .build();
        return folder;
    }

    public static FolderEntity from(@NonNull Long id, @NonNull FolderUpdateRequest request) {
        FolderEntity folder = FolderEntity
            .builder()
            .id(id)
            .name(request.getName())
            .updateCount(request.getUpdateCount())
            .build();
        return folder;
    }
}
