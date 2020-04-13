package com.restful.RestfulApi.error;

public class InvalidTodoIdException extends  Exception {
    public InvalidTodoIdException(int id ) {
        super("InvalidTodoIdException : id " +  id + " not found");
    }

    public InvalidTodoIdException(  ) {
        super("InvalidTodoIdException : id not found");
    }
}
