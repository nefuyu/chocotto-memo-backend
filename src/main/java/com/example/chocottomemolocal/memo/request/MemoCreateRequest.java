package com.example.chocottomemolocal.memo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoCreateRequest {
      @NotBlank
      private String title;
      @NotBlank
      private String content;
}
