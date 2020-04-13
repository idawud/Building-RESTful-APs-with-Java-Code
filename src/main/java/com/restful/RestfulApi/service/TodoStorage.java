package com.restful.RestfulApi.service;

import com.restful.RestfulApi.error.InvalidTodoIdException;
import com.restful.RestfulApi.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List< Todo> getTodos() {
        return jdbcTemplate.query("select * from todo",
                BeanPropertyRowMapper.newInstance(Todo.class));
    }

    public Todo getTodo(Integer id) throws InvalidTodoIdException {
        Todo todo = jdbcTemplate.queryForObject("SELECT * FROM todo WHERE id = ?", new Object[]{id},
                BeanPropertyRowMapper.newInstance(Todo.class));
        if (todo == null) {
            throw new InvalidTodoIdException();
        }
        return todo;
    }

    public Todo addTodo(Todo todo) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("todo").usingGeneratedKeyColumns("id");
        Map<String, Object> insertValue = new HashMap<>();
        insertValue.put("message", todo.getMessage());

        Number number = insert.executeAndReturnKey(insertValue);
        return new Todo(number.intValue(), todo.getMessage());
    }

    public void replace(Todo todo) throws InvalidTodoIdException {
        // update 1 -> success 0 -> error
        int update = jdbcTemplate.update("UPDATE todo SET message = ? WHERE id = ? ", todo.getMessage(), todo.getId());
        if (update <= 0) {
            throw new InvalidTodoIdException(todo.getId());
        }
    }

    public void removeTodo(Integer id) throws InvalidTodoIdException {
        // update 1 -> success 0 -> error
        int update = jdbcTemplate.update("DELETE FROM todo WHERE id = ?", id);
        if (update <= 0) {
            throw new InvalidTodoIdException(id);
        }
    }
}
