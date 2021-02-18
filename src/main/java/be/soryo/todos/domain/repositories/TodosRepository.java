package be.soryo.todos.domain.repositories;

import be.soryo.todos.application.dto.TodoCreateDTO;
import be.soryo.todos.domain.models.Todo;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface TodosRepository {
  public Multi<Todo> listTodos();

  public Uni<Todo> getTodo(Long id);

  public Uni<Long> createTodo(TodoCreateDTO todo);
}
