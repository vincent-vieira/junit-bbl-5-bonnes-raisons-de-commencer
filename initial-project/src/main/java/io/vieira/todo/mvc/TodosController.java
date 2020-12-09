package io.vieira.todo.mvc;

import io.vieira.todo.TodosService;
import io.vieira.todo.models.TodoItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodosController {

    private final TodosService todosService;
    private final TodoItemMapper todoMapper;

    public TodosController(TodosService todosService) {
        this.todosService = todosService;
        this.todoMapper = new TodoItemMapper("/todos");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodo(@PathVariable("id") UUID id, UriComponentsBuilder componentsBuilder) {
        return ResponseEntity.of(todosService.findTodo(id).map(todoMapper.apply(componentsBuilder)));
    }

    @GetMapping
    public List<TodoItem> getTodos(UriComponentsBuilder componentsBuilder) {
        return todosService.findTodos().stream().map(todoMapper.apply(componentsBuilder)).collect(Collectors.toList());
    }
}
