package com.murielxun.rest.webservice.restfulwebservices.todo;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TodoResource {
	@Autowired
	private TodoHardcodedService todoService;

	@GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) {
		return todoService.findAll();
	}

	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {
		Todo todo = todoService.deleteById(id);
		if (todo != null) {
			// return a 204
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();

	}

	@GetMapping("/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) {
		return todoService.findById(id);
	}

	@PutMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id,
			@RequestBody Todo todo) {

		Todo todoUpdatedTodo = todoService.save(todo);
		return new ResponseEntity<Todo>(todoUpdatedTodo, HttpStatus.OK);
	}

	@PostMapping("/users/{username}/todos")
	public ResponseEntity<Todo> createTodo(@PathVariable String username, @RequestBody Todo todo) {

		Todo createdTodo = todoService.save(todo);

		// location
		// get current resource url
		// {id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}
}