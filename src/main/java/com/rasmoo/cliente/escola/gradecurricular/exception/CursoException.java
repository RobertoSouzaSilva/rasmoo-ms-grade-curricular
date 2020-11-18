package com.rasmoo.cliente.escola.gradecurricular.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CursoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus httpStatus;

	public CursoException(HttpStatus httpStatus, String mensagem) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}
	
	

}
