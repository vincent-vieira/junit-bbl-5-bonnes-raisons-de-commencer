package io.vieira.todo;

import io.vieira.todo.models.Todo;
import io.vieira.todo.models.TodoData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefaultTodosService implements TodosService {

    private final TodosRepository todosRepository;

    private final Function<TodoData, Todo> todoDataMapper = todoData -> new Todo(todoData.getId(), todoData.getTitle(), todoData.getOrder(), todoData.isCompleted());

    public DefaultTodosService(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    @Override
    public Optional<Todo> findTodo(UUID id) {
        return todosRepository.findById(id).map(todoDataMapper);
    }

    @Override
    public List<Todo> findTodos() {
        return StreamSupport
                .stream(todosRepository.findAll().spliterator(), false)
                .map(todoDataMapper)
                .collect(Collectors.toList());
    }
}
