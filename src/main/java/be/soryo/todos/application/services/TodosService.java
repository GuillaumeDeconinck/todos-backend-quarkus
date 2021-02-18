package be.soryo.todos.application.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

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

  private static final Logger logger = Logger.getLogger(TodosService.class);

  public Multi<Todo> listTodos() {
    return todosDao.listTodos();
  }

  public Uni<Todo> getTodo(Long id) {
    return todosDao.getTodo(id);
  }

  public Uni<Long> createTodo(TodoCreateDTO todo) {
    logger.info("Received a new Todo");
    return todosDao.createTodo(todo);
  }
}
