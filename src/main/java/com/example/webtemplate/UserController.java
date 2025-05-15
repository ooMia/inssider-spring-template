package com.example.webtemplate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
class UserController {

	private final UserRepository repository;

	UserController(UserRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/users")
	CollectionModel<EntityModel<User>> all() {

		List<EntityModel<User>> users = repository.findAll().stream()
				.map(employee -> EntityModel.of(employee,
						linkTo(methodOn(UserController.class).one(employee.getId())).withSelfRel(),
						linkTo(methodOn(UserController.class).all()).withRel("users")))
				.collect(Collectors.toList());

		return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/users")
	User newEmployee(@RequestBody User newEmployee) {
		return repository.save(newEmployee);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/users/{id}")
	EntityModel<User> one(@PathVariable Long id) {

		User employee = repository.findById(id) //
				.orElseThrow(() -> new UserNotFoundException(id));

		return EntityModel.of(employee, //
				linkTo(methodOn(UserController.class).one(id)).withSelfRel(),
				linkTo(methodOn(UserController.class).all()).withRel("users"));
	}
	// end::get-single-item[]

	@PutMapping("/users/{id}")
	User replaceEmployee(@RequestBody User newEmployee, @PathVariable Long id) {

		return repository.findById(id) //
				.map(employee -> {
					employee.setName(newEmployee.getName());
					employee.setRole(newEmployee.getRole());
					return repository.save(employee);
				}) //
				.orElseGet(() -> {
					return repository.save(newEmployee);
				});
	}

	@DeleteMapping("/users/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}