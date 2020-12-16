package io.vieira.todo.mvc;

import io.vieira.todo.models.Todo;
import io.vieira.todo.models.TodoItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Todo item mapper")
class TodoItemMapperTest {

    @Mock
    private UriComponentsBuilder componentsBuilder;

    @Mock
    private UriComponents uriComponents;

    public static Stream<Arguments> baseUrls() {
        return Stream.of("/todos", "/io.vieira.todo", "/test").map(Arguments::of);
    }

    @DisplayName("should generate todo with url")
    @ParameterizedTest(name = "of value {0}")
    @MethodSource("baseUrls")
    @ValueSource(strings = {"/todos", "/io.vieira.todo", "/test"})
    @CsvSource("/todos, /io.vieira.todo, /test")
    @EmptySource
    void shouldGenerateTheProperUrlForATodo(String baseUrl) {
        final var uuid = UUID.randomUUID();
        final var expectedItem = new TodoItem(uuid, "Title", 1, false, "http://localhost" + baseUrl + "/" + uuid);

        when(componentsBuilder.replacePath(any())).thenReturn(componentsBuilder);
        when(componentsBuilder.pathSegment(any())).thenReturn(componentsBuilder);
        when(uriComponents.toUriString()).thenReturn(expectedItem.getUrl());
        when(componentsBuilder.build()).thenReturn(uriComponents);

        final var mapper = new TodoItemMapper(baseUrl);
        final var todo = new Todo(uuid, "Title", 1, false);
        assertEquals(expectedItem, mapper.apply(componentsBuilder).apply(todo));

        final var inOrder = inOrder(componentsBuilder, uriComponents);
        inOrder.verify(componentsBuilder).replacePath(eq(baseUrl));
        inOrder.verify(componentsBuilder).pathSegment(todo.getId().toString());
        inOrder.verify(componentsBuilder).build();
        inOrder.verify(uriComponents).toUriString();

        inOrder.verifyNoMoreInteractions();
    }

    @ParameterizedTest(name = "of value {0}")
    @DisplayName("should not generate a todo if it is")
    @NullSource
    // For the sake of the demo. This test is kinda useless otherwise
    void shouldNotGenerateATodoIfNull(Todo todoItem) {
        final var mapper = new TodoItemMapper("");

        assertThrows(NullPointerException.class, () -> mapper.apply(componentsBuilder).apply(todoItem));
    }
}
