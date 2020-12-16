package io.vieira.todo;

import io.vieira.todo.models.TodoData;

import java.util.Optional;
import java.util.UUID;

public interface TodosRepository {

    Iterable<TodoData> findAll();

    Optional<TodoData> findById(UUID id);
}
