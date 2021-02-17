package be.soryo.todos.infrastructure.pgsql;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import be.soryo.todos.application.dto.TodoCreateDTO;
import be.soryo.todos.domain.models.Todo;
import io.quarkus.reactive.datasource.ReactiveDataSource;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

@ApplicationScoped
public class TodosDAO {
  @Inject
  @ReactiveDataSource("postgresTodos")
  PgPool client;

  public Multi<Todo> listTodos() {
    return client.query("SELECT * FROM todos").execute()
      .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
      .onItem().transform(Todo::from);
  }

  public Uni<Todo> getTodo(Long id) {
    return client.preparedQuery("SELECT * FROM todos WHERE id = $1").execute(Tuple.of(id))
      .onItem().transform(RowSet::iterator)
      .onItem().transform(iterator -> iterator.hasNext() ? Todo.from(iterator.next()) : null);
  }

  public Uni<Long> createTodo(TodoCreateDTO todo) {
    return client.preparedQuery("INSERT INTO todos (title, completed) VALUES ($1, $2) RETURNING (id)")
      .execute(Tuple.of(todo.title, todo.completed.booleanValue()))
      .onItem().transform(set -> set.iterator().next().getLong("id"));
  }
}
