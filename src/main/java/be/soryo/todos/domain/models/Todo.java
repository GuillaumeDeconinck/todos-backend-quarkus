package be.soryo.todos.domain.models;

import io.vertx.mutiny.sqlclient.Row;

public class Todo {
  Long id;
  String title;
  boolean completed;

  public Todo(Long id, String title, boolean completed) {
    this.id = id;
    this.title = title;
    this.completed = completed;
  }

  public Todo(String title, boolean completed) {
    this.title = title;
    this.completed = completed;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public boolean getCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public static Todo from(Row row) {
    return new Todo(row.getLong("id"), row.getString("title"), row.getBoolean("completed"));
  }
}
