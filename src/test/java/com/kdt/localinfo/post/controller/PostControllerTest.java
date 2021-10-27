package com.kdt.localinfo.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.localinfo.post.Entity.Category;
import com.kdt.localinfo.post.dto.PostDto;
import com.kdt.localinfo.post.repository.CategoryRepository;
import com.kdt.localinfo.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long savedPostId;

    private PostDto postDto;

    private Category category;

    @BeforeEach
    void saveSampleData() {
        category = categoryRepository.findById(1L).get();

        postDto = PostDto.builder()
                .id(1L)
                .contents("this is sampel post")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .category(category)
                .build();

        savedPostId = postService.createPost(postDto);
    }
    @Test
    void write() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}