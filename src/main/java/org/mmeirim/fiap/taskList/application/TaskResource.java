package org.mmeirim.fiap.taskList.application;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mmeirim.fiap.taskList.domain.Task;
import org.mmeirim.fiap.taskList.domain.TaskRepository;

import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
    @Inject
    TaskRepository taskRepository;

    @POST
    public Response create(TaskDTO dto) {
        Task task = new Task(dto.description);
        Task savedTask = taskRepository.save(task);
        return Response.status(Response.Status.CREATED).entity(savedTask).build();
    }

    @GET
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return taskRepository.findById(id)
                .map(task -> Response.ok(task).build())
                .orElse(Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Task com esse Id não encontrada\"}")
                        .build());
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TaskDTO dto) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setDescription(dto.description);
                    if ("CONCLUIDA".equals(dto.status)) {
                        task.close();
                    } else {
                        task.reopen();
                    }
                    taskRepository.update(task);
                    return Response.ok(task).build();
                })
                .orElse(Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Task com esse Id não encontrada\"}")
                        .build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        taskRepository.deleteById(id);
        return Response.noContent().build();
    }
}

