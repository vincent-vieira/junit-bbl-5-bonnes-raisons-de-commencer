package io.vieira.todo.models;

import java.util.Objects;
import java.util.UUID;

public class TodoData {

    private final UUID id;

    private final String title;

    private final int order;

    private final boolean completed;

    public TodoData(UUID id, String title, int order, boolean completed) {
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
        if (!(o instanceof TodoData)) return false;
        TodoData todoData = (TodoData) o;
        return order == todoData.order && completed == todoData.completed && Objects.equals(id, todoData.id) && Objects.equals(title, todoData.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, order, completed);
    }
}
