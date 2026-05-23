package com.example.chocottomemolocal.memo.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.chocottomemolocal.memo.controller.MemoController;
import com.example.chocottomemolocal.memo.entity.MemoEntity;
import com.example.chocottomemolocal.memo.request.MemoCreateRequest;
import com.example.chocottomemolocal.memo.request.MemoUpdateRequest;
import com.example.chocottomemolocal.memo.response.MemoDto;
import com.example.chocottomemolocal.memo.response.MemoResponse;
import com.example.chocottomemolocal.memo.response.MemosResponse;
import com.example.chocottomemolocal.memo.service.MemoService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(MemoController.class)
class MemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemoService memoService;

    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private static final String TITLE_1 = "__TITLE_1__";
    private static final String TITLE_2 = "__TITLE_2__";
    private static final String CONTENT_1 = "__CONTENT_1__";
    private static final String CONTENT_2 = "__CONTENT_2__";
    private static final long CREATED_AT_1 = 111111111L;
    private static final long CREATED_AT_2 = 222222222L;
    private static final long UPDATED_AT_1 = 111111112L;
    private static final long UPDATED_AT_2 = 222222223L;
    private static final long UPDATE_COUNT_1 = 4321L;
    private static final long UPDATE_COUNT_2 = 6789L;

    @Nested
    class GetAll {
        @Test
        void success() throws Exception {
            final MemoEntity memo1 = MemoEntity.builder().id(ID_1).title(TITLE_1).content(CONTENT_1)
                .isDeleted(false).createdAt(CREATED_AT_1).updatedAt(UPDATED_AT_1).updateCount(UPDATE_COUNT_1).build();
            final MemoEntity memo2 = MemoEntity.builder().id(ID_2).title(TITLE_2).content(CONTENT_2)
                .isDeleted(false).createdAt(CREATED_AT_2).updatedAt(UPDATED_AT_2).updateCount(UPDATE_COUNT_2).build();
            doReturn(List.of(memo1, memo2)).when(memoService).findAll();

            final MvcResult result = mockMvc.perform(get("/api/memos"))
                .andExpect(status().isOk()).andReturn();
            final String json = result.getResponse().getContentAsString();
            final MemosResponse actual = objectMapper.readValue(json, MemosResponse.class);

            assertThat(actual.getMemos())
                .hasSize(2)
                .extracting(MemoDto::getId, MemoDto::getTitle, MemoDto::getCreatedAt, MemoDto::getUpdatedAt)
                .containsExactly(
                    tuple(ID_1, TITLE_1, CREATED_AT_1, UPDATED_AT_1),
                    tuple(ID_2, TITLE_2, CREATED_AT_2, UPDATED_AT_2)
                );
        }

        @Test
        void empty() throws Exception {
            doReturn(List.of()).when(memoService).findAll();
            final MvcResult result = mockMvc.perform(get("/api/memos"))
                .andExpect(status().isOk()).andReturn();
            final String json = result.getResponse().getContentAsString();
            final MemosResponse actual = objectMapper.readValue(json, MemosResponse.class);

            assertThat(actual.getMemos()).isEmpty();
        }
    }

    @Nested
    class GetById {
        @Test
        void success() throws Exception {
            final MemoEntity memo1 = MemoEntity.builder().id(ID_1).title(TITLE_1).content(CONTENT_1)
                .isDeleted(false).createdAt(CREATED_AT_1).updatedAt(UPDATED_AT_1).updateCount(UPDATE_COUNT_1).build();
            
            doReturn(memo1).when(memoService).findById(ID_1);

            final MvcResult result = mockMvc.perform(get("/api/memos/{id}", ID_1))
                    .andExpect(status().isOk()).andReturn();
            final String json = result.getResponse().getContentAsString();
            final MemoResponse actual = objectMapper.readValue(json, MemoResponse.class);

            assertThat(actual.getId()).isEqualTo(ID_1);
            assertThat(actual.getTitle()).isEqualTo(TITLE_1);
            assertThat(actual.getContent()).isEqualTo(CONTENT_1);
            assertThat(actual.getCreatedAt()).isEqualTo(CREATED_AT_1);
            assertThat(actual.getUpdatedAt()).isEqualTo(UPDATED_AT_1);
            assertThat(actual.getUpdateCount()).isEqualTo(UPDATE_COUNT_1);
        }

        @Test
        void notFound() throws Exception {
            doReturn(null).when(memoService).findById(ID_1);

            mockMvc.perform(get("/api/memos/{id}", ID_1))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Create {
        @Test
        void success() throws Exception {
            doReturn(ID_1).when(memoService).create(any());

            final MemoCreateRequest request = MemoCreateRequest.builder().title(TITLE_1).content(CONTENT_1).build();
            mockMvc.perform(post("/api/memos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/memos/" + ID_1));
        }

        @ParameterizedTest
        @CsvSource({
            "'', ''",
            "'', 'CONTENT'",
            "'TITLE', ''"
        })
        void validationError(String title, String content) throws Exception {
            final MemoCreateRequest request = 
                MemoCreateRequest.builder().title(title).content(content).build();

            mockMvc.perform(post("/api/memos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Update {
        @Test
        void success() throws Exception {
            final MemoEntity memo1 = MemoEntity.builder().id(ID_1).title(TITLE_1).content(CONTENT_1)
                .isDeleted(false).createdAt(CREATED_AT_1).updatedAt(UPDATED_AT_1).updateCount(UPDATE_COUNT_1).build();
            doReturn(memo1).when(memoService).update(any());
            final MemoUpdateRequest request = 
                MemoUpdateRequest.builder().title(TITLE_1).content(CONTENT_1).updateCount(0L).build();
            final MvcResult result = mockMvc.perform(put("/api/memos/{id}", ID_1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andReturn();
            final String json = result.getResponse().getContentAsString();
            final MemoResponse actual = objectMapper.readValue(json, MemoResponse.class);

            assertThat(actual.getId()).isEqualTo(ID_1);
            assertThat(actual.getTitle()).isEqualTo(TITLE_1);
            assertThat(actual.getContent()).isEqualTo(CONTENT_1);
            assertThat(actual.getCreatedAt()).isEqualTo(CREATED_AT_1);
            assertThat(actual.getUpdatedAt()).isEqualTo(UPDATED_AT_1);
            assertThat(actual.getUpdateCount()).isEqualTo(UPDATE_COUNT_1);
        }

        @Test
        void notFound() throws Exception {
            doReturn(null).when(memoService).update(any());

            final MemoUpdateRequest request = 
                MemoUpdateRequest.builder().title(TITLE_1).content(CONTENT_1).updateCount(0L).build();
            mockMvc.perform(put("/api/memos/{id}", ID_1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());
        }

        @ParameterizedTest
        @CsvSource({
            "'', ''",
            "'', 'CONTENT'",
            "'TITLE', ''"
        })
        void validationError(String title, String content) throws Exception {
            final MemoUpdateRequest request = 
                MemoUpdateRequest.builder().title(title).content(content).updateCount(0L).build();
            mockMvc.perform(put("/api/memos/{id}", ID_1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Delete {
        // todo
    }
}
