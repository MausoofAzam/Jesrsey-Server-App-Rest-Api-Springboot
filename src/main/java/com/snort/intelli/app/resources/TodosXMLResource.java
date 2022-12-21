package com.snort.intelli.app.resources;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.snort.intelli.app.repository.TodosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.snort.intelli.app.entites.Todos;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Component
@Path("/xml/todos/restapi/server")
public class TodosXMLResource {

	private Logger log = LoggerFactory.getLogger(TodosXMLResource.class);

	//JPA repository
	@Autowired
	private TodosRepository todosRepository;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/createTask")
	public Response createTask(@RequestBody Todos todos) {
		log.info("TodosController : createTask executed!");
		if(todos.getAssignedDate()==null) todos.setAssignedDate(new Date());
		Todos creTodos1 = todosRepository.save(todos);
		if(creTodos1.getTaskId()!=null){
			GenericEntity<Todos> entity = new GenericEntity<Todos>(creTodos1){};
			return Response.status(201).entity(entity).build();
		}
		return Response.status(400).entity("Todos details not completed!").build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/updateOneTodo")
	public Response updateOneTodo(@RequestBody Todos newTodo) {
		log.info("TodosController : updateOneTodo executed!");
		newTodo.setUpdatedDate(new Date());
		if(todosRepository.existsById(newTodo.getTaskId())){
			GenericEntity<Todos> entity = new GenericEntity<Todos>(newTodo){};
			return Response.status(200).entity(entity).build();
		}
		return Response.status(404).entity("Todos not found!").build();
	}


	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/findAll")
	public Response findAll() {
		log.info("TodosController : findAll executed!");
		List<Todos> todosList = (List<Todos>) todosRepository.findAll();
		GenericEntity<List<Todos>> entity = new GenericEntity<List<Todos>>(todosList){};
		log.info("Todos["+entity.getEntity().size()+"]");
		return Response.status(200).entity(entity).build();
	}


	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/findOneTodo/{id}")
	public Response findOneTodo(@PathParam("id") Long id) {
		log.info("TodosController : findOneTodo executed!");
		Optional<Todos> todos = todosRepository.findById(id);
		if(todos.isPresent()){
			GenericEntity<Todos> entity = new GenericEntity<Todos>(todos.get()){};
			return Response.status(200).entity(entity).build();
		}else{
			return Response.status(404).entity("Todos not found with id : "+id).build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_XML)
	@Path("/deleteOneTodo/{id}")
	public Response deleteOneTodo(@PathParam("id") Long id) {
		log.info("TodosController : deleteOneTodo executed!");
		Optional<Todos> todos = todosRepository.findById(id);
		if(todos.isPresent()){
			todosRepository.delete(todos.get());
			return Response.status(200).entity("Todos deleted["+id+"] successfully!").build();
		}else{
			return Response.status(404).entity("Todos not found with id : "+id).build();
		}
	}

	
}
