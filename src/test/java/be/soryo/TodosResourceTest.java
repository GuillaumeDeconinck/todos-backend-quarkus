package be.soryo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import be.soryo.todos.application.dto.TodoCreateDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TodosResourceTest {

  TodoCreateDTO initialTodo;
  TodoCreateDTO updatedTodo;
  String todoId;

  @Test
  @Order(1)
  public void testListTodosEndpoint() {
    given().when().get("/todos").then().statusCode(200).body(is("[]"));
  }

  @Test
  @Order(2)
  public void testCreateTodoEndpoint() {
    initialTodo = new TodoCreateDTO("Test todo", false);
    ValidatableResponse response = given().when().contentType(ContentType.JSON).accept(ContentType.JSON)
        .body(initialTodo).post("/todos").then().statusCode(201).header("Location", "http://localhost:8081/todos/1")
        .body(is(""));
    this.todoId = response.extract().header("Location").replace("http://localhost:8081/todos/", "");
  }

  @Test
  @Order(3)
  public void testGetTodoEndpoint() {
    given().when().contentType(ContentType.JSON).accept(ContentType.JSON).get("/todos/" + this.todoId).then().statusCode(200)
        .body("title", is(initialTodo.title)).body("completed", is(initialTodo.completed));
  }

  @Test
  @Order(4)
  public void testUpdateEndpoint() {
    updatedTodo = new TodoCreateDTO("Test todo updated", true);
    given().when().contentType(ContentType.JSON).accept(ContentType.JSON).body(updatedTodo).put("/todos/" + this.todoId)
        .then().statusCode(204).body(is(""));
  }

  @Test
  @Order(5)
  public void testGetEndpoint_Updated() {
    given().when().contentType(ContentType.JSON).accept(ContentType.JSON).get("/todos/" + this.todoId).then().statusCode(200)
        .body("title", is(updatedTodo.title)).body("completed", is(updatedTodo.completed));
  }

  @Test
  @Order(6)
  public void testDeleteEndpoint() {
    given().when().contentType(ContentType.JSON).accept(ContentType.JSON).delete("/todos/" + this.todoId).then()
        .statusCode(204).body(is(""));
  }

  @Test
  @Order(7)
  public void testGetEndpoint_Deleted() {
    given().when().contentType(ContentType.JSON).accept(ContentType.JSON).get("/todos/" + this.todoId).then()
        .statusCode(404);
  }
}