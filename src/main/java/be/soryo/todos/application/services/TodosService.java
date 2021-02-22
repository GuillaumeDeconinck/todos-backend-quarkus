package be.soryo.todos.application.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import be.soryo.todos.application.dto.TodoCreateDTO;
import be.soryo.todos.domain.models.Todo;
import be.soryo.todos.infrastructure.pgsql.TodosDAO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class TodosService {
  @Inject
  @Named("TodosRepositoryImpl")
  TodosDAO todosDao;

  public Multi<Todo> listTodos() {
    return todosDao.listTodos();
  }

  public Uni<Todo> getTodo(Long id) {
    return todosDao.getTodo(id);
  }

  public Uni<Long> createTodo(TodoCreateDTO todo) {
    return todosDao.createTodo(todo);
  }

  public Uni<Void> updateTodo(Long id, TodoCreateDTO todo) {
    return todosDao.updateTodo(id, todo);
  }

  public Uni<Void> patchTodo(Long id, TodoCreateDTO todo) {
    return this.getTodo(id)
      .onItem().transform(oldTodo -> {
        todo.title = todo.title != null ? todo.title : oldTodo.getTitle();
        todo.completed = todo.completed != null ? todo.completed : oldTodo.getCompleted();
        return updateTodo(id, todo);
      })
      .onItem().transform(v -> null);
  }

  public Uni<Void> deleteTodo(Long id) {
    return todosDao.deleteTodo(id);
  }
}
