package be.soryo.todos.infrastructure.rest;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import be.soryo.todos.application.dto.TodoCreateDTO;
import be.soryo.todos.application.services.TodosService;
import be.soryo.todos.domain.models.Todo;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodosResource {

    private static final Logger logger = Logger.getLogger(TodosResource.class);

    @Inject
    TodosService todosService;

    @GET
    public Multi<Todo> listTodos() {
        return todosService.listTodos();
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getTodo(int id) {
        return todosService.getTodo(Long.valueOf(id))
            .onItem().transform(todo -> todo != null ? Response.ok(todo) : Response.status(Status.NOT_FOUND))
            .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> createTodo(TodoCreateDTO todo) {
        logger.info("Received a new Todo (rest)");
        return todosService.createTodo(todo)
            .onItem().transform(id -> URI.create("/todos/" + id))
            .onItem().transform(uri -> Response.created(uri).build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteTodo(int id) {
        return todosService.getTodo(Long.valueOf(id))
            .onItem().transform(todo -> todo != null ? Response.ok(todo) : Response.status(Status.NOT_FOUND))
            .onItem().transform(ResponseBuilder::build);
    }
}