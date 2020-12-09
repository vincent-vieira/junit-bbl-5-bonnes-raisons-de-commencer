package io.vieira.todo.mvc;

import io.vieira.todo.models.Todo;
import io.vieira.todo.models.TodoItem;
import org.junit.Rule;
import org.junit.Test;
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
    private final Todo todo;
    private final TodoItem expectedItem;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private UriComponentsBuilder componentsBuilder;

    @Mock
    private UriComponents uriComponents;

    public TodoItemMapperTest(String baseUrl, Todo todo, TodoItem expectedItem) {
        this.baseUrl = baseUrl;
        this.todo = todo;
        this.expectedItem = expectedItem;
    }

    @Parameterized.Parameters
    public static List<Object[]> baseUrls() {
        return Stream
                .of(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
                .flatMap(uuid -> Stream
                        .of("/todos", "/todo", "/test")
                        .map(baseUrl -> new Object[]{
                                baseUrl,
                                new Todo(uuid, "Title", 1, false),
                                new TodoItem(uuid, "Title", 1, false, "http://localhost" + baseUrl + "/" + uuid)
                        })
                )
                .collect(Collectors.toList());
    }

    @Test
    public void shouldGenerateTheProperUrlForATodo() {
        when(componentsBuilder.replacePath(any())).thenReturn(componentsBuilder);
        when(componentsBuilder.pathSegment(any())).thenReturn(componentsBuilder);
        when(uriComponents.toUriString()).thenReturn(expectedItem.getUrl());
        when(componentsBuilder.build()).thenReturn(uriComponents);

        final var mapper = new TodoItemMapper(baseUrl);
        assertEquals(expectedItem, mapper.apply(componentsBuilder).apply(todo));

        final var inOrder = inOrder(componentsBuilder, uriComponents);
        inOrder.verify(componentsBuilder).replacePath(eq(baseUrl));
        inOrder.verify(componentsBuilder).pathSegment(todo.getId().toString());
        inOrder.verify(componentsBuilder).build();
        inOrder.verify(uriComponents).toUriString();

        inOrder.verifyNoMoreInteractions();
    }
}
