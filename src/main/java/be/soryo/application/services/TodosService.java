package be.soryo.application.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import be.soryo.domain.models.Todo;
import be.soryo.todos.infrastructure.pgsql.TodosDAO;
import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class TodosService {
  @Inject
  TodosDAO todosDao;

  public Multi<Todo> listTodos() {
    return todosDao.listTodos();
  }
}
