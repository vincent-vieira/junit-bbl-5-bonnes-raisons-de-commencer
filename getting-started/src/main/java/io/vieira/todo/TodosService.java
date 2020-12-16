package io.vieira.todo;

import io.vieira.todo.models.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodosService {
    Optional<Todo> findTodo(UUID id);

    List<Todo> findTodos();
}
