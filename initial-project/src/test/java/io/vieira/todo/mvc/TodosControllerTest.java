package io.vieira.todo.mvc;

import io.vieira.todo.TodosService;
import io.vieira.todo.models.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TodosController.class)
public class TodosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodosService todosService;

    @Test
    public void givenTodosShouldBeReturnedWhenGettingThem() throws Exception {
        when(todosService.findTodos()).thenReturn(List.of(
                new Todo(UUID.fromString("1f3d564d-aeb8-4fd2-ba89-0065a8f9a9b4"), "Title 1", 1, false),
                new Todo(UUID.fromString("4641ecbd-2ef6-4b68-8254-f0cf486e4f17"), "Title 2", 2, true)
        ));

        mockMvc.perform(get("/todos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", contains("1f3d564d-aeb8-4fd2-ba89-0065a8f9a9b4", "4641ecbd-2ef6-4b68-8254-f0cf486e4f17")))
                .andExpect(jsonPath("$[*].title", contains("Title 1", "Title 2")))
                .andExpect(jsonPath("$[*].url", contains("http://localhost/todos/1f3d564d-aeb8-4fd2-ba89-0065a8f9a9b4", "http://localhost/todos/4641ecbd-2ef6-4b68-8254-f0cf486e4f17")))
                .andExpect(jsonPath("$[*].order", contains(1, 2)))
                .andExpect(jsonPath("$[*].completed", contains(false, true)));

        verify(todosService).findTodos();
        verifyNoMoreInteractions(todosService);
    }

    @Test
    public void givenAnKnownIdShouldReturnATodo() throws Exception {
        final var sampleId = UUID.fromString("1f3d564d-aeb8-4fd2-ba89-0065a8f9a9b4");
        when(todosService.findTodo(eq(sampleId))).thenReturn(Optional.of(new Todo(
                sampleId, "Title 1", 1, false
        )));

        mockMvc.perform(get("/todos/{id}", sampleId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value("1f3d564d-aeb8-4fd2-ba89-0065a8f9a9b4"))
                .andExpect(jsonPath("$.title").value("Title 1"))
                .andExpect(jsonPath("$.url").value("http://localhost/todos/1f3d564d-aeb8-4fd2-ba89-0065a8f9a9b4"))
                .andExpect(jsonPath("$.order").value(1))
                .andExpect(jsonPath("$.completed").value(false));

        verify(todosService).findTodo(eq(sampleId));
        verifyNoMoreInteractions(todosService);
    }

    @Test
    // This could be a parameterized test, but is not for the sake of the demo.
    public void givenAnUnknownIdShouldNotReturnAnyTodo() throws Exception {
        when(todosService.findTodo(any())).thenReturn(Optional.empty());

        final var sampleId = UUID.fromString("1f3d564d-aeb8-4fd2-ba89-0065a8f9a9b4");

        mockMvc.perform(get("/todos/{id}", sampleId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(todosService).findTodo(eq(sampleId));
        verifyNoMoreInteractions(todosService);
    }
}
