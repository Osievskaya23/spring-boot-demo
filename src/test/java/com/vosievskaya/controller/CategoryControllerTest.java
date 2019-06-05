package com.vosievskaya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosievskaya.controllers.CategoryController;
import com.vosievskaya.model.Category;
import com.vosievskaya.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void TestThatGetByIdIdShouldReturnCategory() throws Exception {
        Long categoryId = 1L;
        Category category = new Category(categoryId, "Phones", "Very bad phones" ,Collections.emptyList());
        String categoryJson = objectMapper.writeValueAsString(category);

        when(categoryService.getById(anyLong())).thenReturn(Optional.of(category));

        mockMvc.perform(get("/category/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().json(categoryJson));
    }

    @Test
    public void TestThatCreateShouldReturnCategory() throws Exception {
        String uri = "/category";
        Long categoryId = 1L;
        Category category = new Category(null, "Phones", "Very bad phones" ,Collections.emptyList());
        Category persisted = new Category(categoryId, "Phones", "Very bad phones" ,Collections.emptyList());
        String categoryJson = objectMapper.writeValueAsString(category);
        String persistedJson = objectMapper.writeValueAsString(category);

        when(categoryService.create(any(Category.class))).thenReturn(Optional.of(category));

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", String.format("%s/%s", uri, categoryId)))
                .andExpect(content().json(persistedJson));
    }

    @Test
    public void TestThatCreateShouldReturn400OnEmptyCategoryName() throws Exception {
        Category category = new Category(null, null, "Very bad phones" ,Collections.emptyList());
        String categoryJson = objectMapper.writeValueAsString(category);

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isBadRequest());
    }
}
