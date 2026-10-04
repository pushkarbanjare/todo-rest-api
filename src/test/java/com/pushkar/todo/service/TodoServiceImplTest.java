package com.pushkar.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pushkar.todo.dto.TodoRequest;
import com.pushkar.todo.dto.TodoResponse;
import com.pushkar.todo.entity.Todo;
import com.pushkar.todo.exception.ResourceNotFoundException;
import com.pushkar.todo.repository.TodoRepository;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setCompleted(false);
    }

    @Test
    void createTodo_shouldReturnSavedTodo() {
        TodoRequest request = new TodoRequest();
        request.setTitle("Test Todo");
        request.setDescription("Test Description");
        request.setCompleted(false);

        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoResponse response = todoService.createTodo(request);

        assertEquals("Test Todo", response.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void getTodoById_whenExists_shouldReturnTodo() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        TodoResponse response = todoService.getTodoById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Test Todo", response.getTitle());
    }

    @Test
    void getTodoById_whenNotExists_shouldThrowException() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> todoService.getTodoById(99L));
    }

    @Test
    void getAllTodos_shouldReturnList() {
        when(todoRepository.findAll()).thenReturn(List.of(todo));

        List<TodoResponse> result = todoService.getAllTodos();

        assertEquals(1, result.size());
        assertEquals("Test Todo", result.get(0).getTitle());
    }

    @Test
    void deleteTodo_whenNotExists_shouldThrowException() {
        when(todoRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> todoService.deleteTodo(99L));
        verify(todoRepository, never()).deleteById(anyLong());
    }
}