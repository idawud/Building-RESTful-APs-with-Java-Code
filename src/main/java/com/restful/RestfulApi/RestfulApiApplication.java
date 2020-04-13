package com.restful.RestfulApi;

import com.restful.RestfulApi.model.Todo;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RestfulApiApplication {
	private static RestTemplateBuilder builder = new RestTemplateBuilder();

	public static void main(String[] args) {
		Optional<List<Todo>> fectchedTodos = getTodosFromAPI();
		if( fectchedTodos.isPresent()){
			fectchedTodos.get().forEach(
					todo -> System.out.println( "ID -> " + todo.getId() + "\t\tMessage -> " + todo.getMessage())
			);
		}else {
			System.out.println("Empty Todo");
		}
	}

	public static Optional<List<Todo>> getTodosFromAPI(){
		Todo[] todos = builder.build().getForObject("https://javarestfulapi.herokuapp.com/todos", Todo[].class);
		if ( todos == null){
			return Optional.empty();
		}
		return Optional.of(Arrays.asList(todos));
	}
}
