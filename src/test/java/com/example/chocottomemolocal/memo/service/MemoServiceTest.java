package com.example.chocottomemolocal.memo.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.chocottomemolocal.memo.entity.MemoEntity;
import com.example.chocottomemolocal.memo.repository.MemoRepository;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MemoServiceTest {
    @Mock
    private MemoRepository memoRepository;
    @InjectMocks
    private MemoService memoService;

    @Nested
    class createTest {
        private static final long ID = 1L;
        private static final String TITLE = "__TITLE__";
        private static final String CONTENT = "__CONTENT__";
        
        @Test
        void success() {
            final MemoEntity memo = MemoEntity.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();
            doAnswer(invocation -> {
                MemoEntity arg = invocation.getArgument(0);
                arg.setId(ID);
                return 1;
            }).when(memoRepository).save(memo);

            final long actual = memoService.create(memo);
            assertThat(actual).isEqualTo(ID);
        }
    }
}
