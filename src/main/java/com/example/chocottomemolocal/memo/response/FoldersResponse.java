package com.example.chocottomemolocal.memo.response;

import java.util.List;

import com.example.chocottomemolocal.memo.entity.FolderEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoldersResponse {
    private List<FolderDto> folders;

    public static FoldersResponse from(List<FolderEntity> entities) {
        return FoldersResponse.builder()
                .folders(entities.stream().map(FolderDto::from).toList())
                .build();
    }
}
