package com.oraclejava.demodog.dog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DogNotFoundException extends RuntimeException {

	public DogNotFoundException(Long id) {
		super("Dog not found: " + id);
	}

}
