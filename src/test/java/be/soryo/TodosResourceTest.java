package be.soryo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import be.soryo.todos.application.dto.TodoCreateDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class TodosResourceTest {

    @Test
    @Order(1)
    public void testListTodosEndpoint() {
        given()
          .when().get("/todos")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }

    @Test
    @Order(2)
    public void testCreateTodoEndpoint() {
        given()
          .when()
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
          .body(new TodoCreateDTO("Test todo", false))
          .post("/todos")
          .then()
             .statusCode(201)
             .header("Location", "http://localhost:8081/todos/1")
             .body(is(""));
    }

}