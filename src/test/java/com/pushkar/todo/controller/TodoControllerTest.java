package com.pushkar.todo.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createTodo_withValidData_shouldReturn201() throws Exception {
        String requestBody = """
                {
                    "title": "Integration Test Todo",
                    "description": "Testing end to end",
                    "completed": false
                }
                """;

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Integration Test Todo")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void createTodo_withBlankTitle_shouldReturn400() throws Exception {
        String requestBody = """
                {
                    "title": "",
                    "description": "should fail"
                }
                """;

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.title", is("Title must not be blank")));
    }

    @Test
    void getTodoById_whenNotExists_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/todos/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("not found")));
    }

    @Test
    void getAllTodos_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk());
    }
}