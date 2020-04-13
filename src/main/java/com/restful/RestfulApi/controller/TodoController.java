package com.restful.RestfulApi.controller;

import com.restful.RestfulApi.error.InvalidTodoIdException;
import com.restful.RestfulApi.model.Todo;
import com.restful.RestfulApi.service.TodoStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.net.URI;

@RestController
@Api
public class TodoController {
    @Autowired
    private TodoStorage todoStore;

    @GetMapping("/todos")
    @ApiOperation("Returns the todos stored to the server")
    public Collection<Todo> getAllTodos() {
        return todoStore.getTodos().values();
    }

    @PostMapping(value = "/todos")
    @ApiOperation("Persist a new todo to the server")
    public ResponseEntity<Void> createNewTodo(@Validated @RequestBody Todo todo) {
        final Todo createdTodo = todoStore.addTodo(todo);
        final URI uri = URI.create("http://localhost:8080/todos/" + createdTodo.getId());
        return ResponseEntity
                .created(uri)
                .build();
    }

    @GetMapping("/todos/{id}")
    @ApiOperation("Request a todo item by id")
    public ResponseEntity<Todo> getTodo(@PathVariable(value = "id") Integer id) {
        try {
            return ResponseEntity.ok(todoStore.getTodo(id));
        } catch (InvalidTodoIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/todos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update a todo item by id")
    public ResponseEntity<Void> updateTodo(@PathVariable(value = "id") Integer id, @RequestBody Todo todo) {
        try {
            todoStore.replace(new Todo(id, todo.getMessage()));
            return ResponseEntity.noContent().build();
        } catch (InvalidTodoIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/todos/{id}")
    @ApiOperation("Delete a todo item by id")
    public ResponseEntity<Void> deleteTodo(@PathVariable(value = "id") Integer id) {
        try {
            todoStore.removeTodo(id);
            return ResponseEntity.noContent().build();
        } catch (InvalidTodoIdException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
