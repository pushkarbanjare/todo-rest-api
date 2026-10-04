package com.pushkar.todo.service;

import java.util.List;
import com.pushkar.todo.dto.TodoRequest;
import com.pushkar.todo.dto.TodoResponse;

public interface TodoService {
    TodoResponse createTodo(TodoRequest request);
    List<TodoResponse> getAllTodos();
    TodoResponse getTodoById(Long id);
    TodoResponse updateTodo(Long id, TodoRequest request);
    void deleteTodo(Long id);
}
