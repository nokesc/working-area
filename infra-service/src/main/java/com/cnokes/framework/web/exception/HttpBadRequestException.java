package com.cnokes.framework.web.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HttpBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	public HttpBadRequestException(String message) {
		super(message);
	}

	public HttpBadRequestException(List<FieldError> fieldErrors) {
		super();
		this.fieldErrors = fieldErrors;
	}

	public HttpBadRequestException(FieldError fieldError) {
		this(Arrays.asList(fieldError));
	}

	public HttpBadRequestException(String message, List<FieldError> fieldErrors) {
		super(message);
	}
}
