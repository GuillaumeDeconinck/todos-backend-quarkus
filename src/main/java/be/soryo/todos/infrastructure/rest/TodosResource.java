package be.soryo.todos.infrastructure.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/hello-resteasy")
public class TodosResource {

    private static final Logger LOG = Logger.getLogger(TodosResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}