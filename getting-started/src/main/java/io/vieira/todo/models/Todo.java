package io.vieira.todo.models;

import java.util.Objects;
import java.util.UUID;

public class Todo {

    private final UUID id;

    private final String title;

    private final int order;

    private final boolean completed;

    public Todo(UUID id, String title, int order, boolean completed) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.completed = completed;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOrder() {
        return order;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return order == todo.order && completed == todo.completed && Objects.equals(id, todo.id) && Objects.equals(title, todo.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, order, completed);
    }
}
