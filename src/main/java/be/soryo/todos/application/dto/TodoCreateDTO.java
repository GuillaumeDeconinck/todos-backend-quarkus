package be.soryo.todos.application.dto;

public class TodoCreateDTO {
  public String title;
  public Boolean completed;  

  public TodoCreateDTO() {
    // default constructor.
  }

  public TodoCreateDTO(String title, Boolean completed) {
    this.title = title;
    this.completed = completed;
  }
}
