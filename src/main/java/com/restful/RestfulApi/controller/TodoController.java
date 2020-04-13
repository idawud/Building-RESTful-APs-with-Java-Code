package com.restful.RestfulApi.controller;

import com.restful.RestfulApi.error.InvalidTodoIdException;
import com.restful.RestfulApi.model.Todo;
import com.restful.RestfulApi.service.TodoStorage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.net.URI;

@RestController
public class TodoController {
    private TodoStorage todoStore = new TodoStorage();

    /*public TodoController( ) {
        this.todoStore = new TodoStorage();
        this.todoStore.addTodo(new Todo(1, "hello"));
    }*/

    @GetMapping("/todos")
    public Collection<Todo> getAllTodos() {
        return todoStore.getTodos().values();
    }

    @PostMapping(value = "/todos")
    public ResponseEntity<Void> createNewTodo(@Validated @RequestBody Todo todo) {
        final Todo createdTodo = todoStore.addTodo(todo);
        final URI uri = URI.create("http://localhost:8080/todos/" + createdTodo.getId());
        return ResponseEntity
                .created(uri)
                .build();
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable(value = "id") Integer id) {
        try {
            return ResponseEntity.ok(todoStore.getTodo(id));
        } catch (InvalidTodoIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/todos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTodo(@PathVariable(value = "id") Integer id, @RequestBody Todo todo) {
        try {
            todoStore.replace(new Todo(id, todo.getMessage()));
            return ResponseEntity.noContent().build();
        } catch (InvalidTodoIdException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable(value = "id") Integer id) {
        try {
            todoStore.removeTodo(id);
            return ResponseEntity.noContent().build();
        } catch (InvalidTodoIdException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
