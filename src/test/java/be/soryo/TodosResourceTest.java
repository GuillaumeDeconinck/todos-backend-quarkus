package be.soryo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TodosResourceTest {

    @Test
    public void testListTodosEndpoint() {
        given()
          .when().get("/todos")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }

}