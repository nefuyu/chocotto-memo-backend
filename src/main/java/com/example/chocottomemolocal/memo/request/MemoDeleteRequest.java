package com.example.chocottomemolocal.memo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoDeleteRequest {
    @NotNull
    private Long updateCount;
}
