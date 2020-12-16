package io.vieira.todo;

import io.vieira.todo.models.TodoData;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryTodosRepositoryTest {

    @Test
    public void shouldFindTodos() {
        final var todos = List.of(
                new TodoData(UUID.randomUUID(), "Title", 1, false),
                new TodoData(UUID.randomUUID(), "Title", 2, false)
        );
        final var repository = new InMemoryTodosRepository(todos);

        assertEquals(todos, repository.findAll());
    }

    @Test
    public void shouldFindKnownTodo() {
        final var sampleId = UUID.fromString("4ca28bc1-939e-4eb6-bcc5-61393cce8b82");
        final var todos = List.of(
                new TodoData(sampleId, "Title", 1, false),
                new TodoData(UUID.randomUUID(), "Title", 2, false)
        );
        final var repository = new InMemoryTodosRepository(todos);

        final var result = repository.findById(sampleId);
        assertTrue(result.isPresent());
        assertEquals(new TodoData(sampleId, "Title", 1, false), result.get());
    }

    @Test
    public void shouldNotFindUnknownTodo() {
        final var todos = List.of(
                new TodoData(UUID.randomUUID(), "Title", 1, false),
                new TodoData(UUID.randomUUID(), "Title", 2, false)
        );
        final var repository = new InMemoryTodosRepository(todos);

        final var result = repository.findById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }
}
