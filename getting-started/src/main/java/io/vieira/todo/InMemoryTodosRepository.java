package io.vieira.todo;

import io.vieira.todo.models.TodoData;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryTodosRepository implements TodosRepository {

    private final List<TodoData> todos;

    public InMemoryTodosRepository(List<TodoData> initialState) {
        this.todos = initialState;
    }

    public InMemoryTodosRepository() {
        this(Collections.emptyList());
    }

    @Override
    public Iterable<TodoData> findAll() {
        return todos;
    }

    @Override
    public Optional<TodoData> findById(UUID id) {
        return todos.stream().filter(todo -> id.equals(todo.getId())).findFirst();
    }
}
