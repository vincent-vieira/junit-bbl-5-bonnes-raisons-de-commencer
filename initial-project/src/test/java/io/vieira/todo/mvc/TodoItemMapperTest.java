package io.vieira.todo.mvc;

import io.vieira.todo.models.Todo;
import io.vieira.todo.models.TodoItem;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class TodoItemMapperTest {

    private final String baseUrl;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UriComponentsBuilder componentsBuilder;

    @Mock
    private UriComponents uriComponents;

    public TodoItemMapperTest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Parameterized.Parameters
    public static List<Object[]> baseUrls() {
        return Stream.of("/todos", "/io.vieira.todo", "/test", "").map(value -> new Object[]{value}).collect(Collectors.toList());
    }

    @Test
    public void shouldGenerateTheProperUrlForATodo() {
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

    @Test
    // For the sake of the demo. This test is kinda useless otherwise
    public void shouldNotGenerateATodoIfNullIsPassed() {
        expectedException.expect(NullPointerException.class);

        final var mapper = new TodoItemMapper(baseUrl);
        mapper.apply(componentsBuilder).apply(null);
    }
}
