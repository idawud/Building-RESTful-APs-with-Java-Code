package com.restful.RestfulApi.service;

import com.restful.RestfulApi.error.InvalidTodoIdException;
import com.restful.RestfulApi.model.Todo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoStorage {
    AtomicInteger incrementKey = new AtomicInteger(0);
    Map<Integer, Todo> todos = new HashMap<>(20);

    public Map<Integer, Todo> getTodos() {
        return Collections.unmodifiableMap(todos);
    }

    public Todo getTodo(Integer id) throws InvalidTodoIdException {
        if (!todos.containsKey(id)) {
            throw new InvalidTodoIdException();
        }
        return todos.get(id);
    }

    public Todo addTodo(Todo todo) {
        Todo newTodo = new Todo(incrementKey.getAndAdd(1), todo.getMessage());
        todos.put(newTodo.getId(), newTodo);
        return newTodo;
    }

    public void replace(Todo todo) throws InvalidTodoIdException {
        if (!todos.containsKey(todo.getId())) {
            throw new InvalidTodoIdException(todo.getId());
        }
        todos.replace(todo.getId(), todo);
    }

    public void removeTodo(Integer id) throws InvalidTodoIdException {
        if (!todos.containsKey(id)) {
            throw new InvalidTodoIdException();
        }
        todos.remove(id);
    }
}
