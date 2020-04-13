package com.restful.RestfulApi.model;

public class Todo {
    private Integer id;
    private String message;

    public Todo() {
        //Used for serialization
    }

    public Todo(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
