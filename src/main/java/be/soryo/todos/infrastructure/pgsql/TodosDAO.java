package be.soryo.todos.infrastructure.pgsql;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import be.soryo.todos.domain.models.Todo;
import io.quarkus.reactive.datasource.ReactiveDataSource;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

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
}
