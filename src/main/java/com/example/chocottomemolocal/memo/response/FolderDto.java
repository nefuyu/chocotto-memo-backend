package com.example.chocottomemolocal.memo.response;

import com.example.chocottomemolocal.memo.entity.FolderEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FolderDto {
    private long id;
    private String name;
    private long createdAt;
    private long updatedAt;

    public static FolderDto from(FolderEntity entity) {
        return FolderDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
