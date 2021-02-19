package be.soryo.todos.infrastructure.rest;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import be.soryo.todos.application.dto.TodoCreateDTO;
import be.soryo.todos.application.services.TodosService;
import be.soryo.todos.domain.models.Todo;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodosResource {

    @Inject
    TodosService todosService;

    @GET
    public Multi<Todo> listTodos() {
        return todosService.listTodos();
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getTodo(@PathParam("id") int id) {
        return todosService.getTodo(Long.valueOf(id))
            .onItem().transform(todo -> todo != null ? Response.ok(todo) : Response.status(Status.NOT_FOUND))
            .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> createTodo(TodoCreateDTO todo) {
        return todosService.createTodo(todo)
            .onItem().transform(id -> URI.create("/todos/" + id))
            .onItem().transform(uri -> Response.created(uri).build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateTodo(@PathParam("id") int id, TodoCreateDTO todoToUpdate) {
        return todosService.updateTodo(Long.valueOf(id), todoToUpdate)
            .onItem().transform(notUsed -> Response.status(Status.NO_CONTENT))
            .onItem().transform(ResponseBuilder::build);
    }

    // @PATCH
    // @Path("/{id}")
    // public Uni<Response> patchTodo(@PathParam("id") int id, TodoCreateDTO todoToUpdate) {
    //     return todosService.updateTodo(Long.valueOf(id), todoToUpdate)
    //         .onItem().transform(notUsed -> Response.status(Status.NO_CONTENT))
    //         .onItem().transform(ResponseBuilder::build);
    // }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteTodo(@PathParam("id") int id) {
        return todosService.deleteTodo(Long.valueOf(id))
            .onItem().transform(notUsed -> Response.status(Status.NO_CONTENT))
            .onItem().transform(ResponseBuilder::build);
    }
}