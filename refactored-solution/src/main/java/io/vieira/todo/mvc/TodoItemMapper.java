package io.vieira.todo.mvc;

import io.vieira.todo.models.Todo;
import io.vieira.todo.models.TodoItem;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Function;

public class TodoItemMapper implements Function<UriComponentsBuilder, Function<Todo, TodoItem>> {

    private final String baseUrl;

    public TodoItemMapper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Function<Todo, TodoItem> apply(UriComponentsBuilder builder) {
        return todo -> new TodoItem(
                todo.getId(),
                todo.getTitle(),
                todo.getOrder(),
                todo.isCompleted(),
                builder
                        .replacePath(baseUrl)
                        .pathSegment(todo.getId().toString())
                        .build()
                        .toUriString()
        );
    }
}
