package com.cnokes.framework.web.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HttpNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpNotFoundException() {
		super();
	}

	public HttpNotFoundException(String message) {
		super(message);
	}
}
