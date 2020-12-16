package io.vieira.todo;

import io.vieira.todo.models.Todo;
import io.vieira.todo.models.TodoData;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.migrationsupport.conditions.IgnoreCondition;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(IgnoreCondition.class)
class DefaultTodosServiceTest {

    @Mock
    private TodosRepository todosRepository;

    @InjectMocks
    private DefaultTodosService todosService;

    @Test
    void shouldFindTodos() {
        final var sampleId = UUID.randomUUID();
        when(todosRepository.findAll()).thenReturn(List.of(new TodoData(sampleId, "Title", 1, false)));

        final var todos = todosService.findTodos();

        assertEquals(List.of(new Todo(sampleId, "Title", 1, false)), todos);

        verify(todosRepository).findAll();
        verifyNoMoreInteractions(todosRepository);
    }

    // This could be a parameterized test, but is not for the sake of the demo.
    @Test
    void shouldFindATodo() {
        final var sampleId = UUID.randomUUID();
        when(todosRepository.findById(eq(sampleId))).thenReturn(Optional.of(new TodoData(sampleId, "Title", 1, false)));

        final var todo = todosService.findTodo(sampleId);

        assertEquals(new Todo(sampleId, "Title", 1, false), todo.orElseThrow(IllegalStateException::new));

        verify(todosRepository).findById(eq(sampleId));
        verifyNoMoreInteractions(todosRepository);
    }

    @Disabled
    @Test
    void shouldBeImplemented() {
        fail("This is a test");
    }

    @Ignore
    @Test
    void shouldBeAlsoImplemented() {
        fail("This is another test");
    }
}
