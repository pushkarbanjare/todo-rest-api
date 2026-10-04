package com.pushkar.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data   
public class TodoRequest {
    @NotBlank (message = "Title must not be blank")
    private String title;
    private String description;
    private boolean completed;
}
