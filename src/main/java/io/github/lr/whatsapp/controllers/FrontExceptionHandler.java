package io.github.lr.whatsapp.controllers;

import javax.validation.ConstraintViolationException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ConditionalOnProperty(value="app.front-controller", havingValue="true")
public class FrontExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String RESPONSE_ERROR_MSG = "The action could not be completed";

	@ExceptionHandler(value=ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException cve, WebRequest request) {
		return handleExceptionInternal(cve, String.format("%s. %s", RESPONSE_ERROR_MSG, cve.getMessage()), headerTextPlain(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value=MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException matme, WebRequest request) {
		return handleExceptionInternal(matme, RESPONSE_ERROR_MSG, headerTextPlain(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value=RuntimeException.class)
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException re, WebRequest request) {
		return handleExceptionInternal(re, RESPONSE_ERROR_MSG, headerTextPlain(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	private HttpHeaders headerTextPlain() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.TEXT_PLAIN);
		return headers;
	}
	
}