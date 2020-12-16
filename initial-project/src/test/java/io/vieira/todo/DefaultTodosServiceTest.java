package io.vieira.todo;

import io.vieira.todo.models.Todo;
import io.vieira.todo.models.TodoData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTodosServiceTest {

    @Mock
    private TodosRepository todosRepository;

    @InjectMocks
    private DefaultTodosService todosService;

    @Test
    public void shouldFindTodos() {
        final var sampleId = UUID.randomUUID();
        when(todosRepository.findAll()).thenReturn(List.of(new TodoData(sampleId, "Title", 1, false)));

        final var todos = todosService.findTodos();

        assertEquals(List.of(new Todo(sampleId, "Title", 1, false)), todos);

        verify(todosRepository).findAll();
        verifyNoMoreInteractions(todosRepository);
    }

    // This could be a parameterized test, but is not for the sake of the demo.
    @Test
    public void shouldFindATodo() {
        final var sampleId = UUID.randomUUID();
        when(todosRepository.findById(eq(sampleId))).thenReturn(Optional.of(new TodoData(sampleId, "Title", 1, false)));

        final var todo = todosService.findTodo(sampleId);

        assertEquals(new Todo(sampleId, "Title", 1, false), todo.orElseThrow(IllegalStateException::new));

        verify(todosRepository).findById(eq(sampleId));
        verifyNoMoreInteractions(todosRepository);
    }

    @Ignore
    @Test
    public void shouldBeImplemented() {
        fail("This is a test");
    }

    @Ignore
    @Test
    public void shouldBeAlsoImplemented() {
        fail("This is another test");
    }
}
