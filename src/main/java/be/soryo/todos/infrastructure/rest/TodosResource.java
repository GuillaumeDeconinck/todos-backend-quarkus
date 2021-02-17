package be.soryo.todos.infrastructure.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import be.soryo.application.services.TodosService;
import be.soryo.domain.models.Todo;
import io.smallrye.mutiny.Multi;

@Path("/todos")
public class TodosResource {

    @Inject
    TodosService todosService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Todo> hello() {
        return todosService.listTodos();
    }
}