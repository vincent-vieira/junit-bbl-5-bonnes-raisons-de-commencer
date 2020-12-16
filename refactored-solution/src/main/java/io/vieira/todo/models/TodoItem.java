package io.vieira.todo.models;

import java.util.Objects;
import java.util.UUID;

public class TodoItem {

    private final UUID id;

    private final String title;

    private final int order;

    private final boolean completed;

    private final String url;

    public TodoItem(UUID id, String title, int order, boolean completed, String url) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.completed = completed;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoItem)) return false;
        TodoItem todoItem = (TodoItem) o;
        return order == todoItem.order && completed == todoItem.completed && Objects.equals(id, todoItem.id) && Objects.equals(title, todoItem.title) && Objects.equals(url, todoItem.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, order, completed, url);
    }
}
