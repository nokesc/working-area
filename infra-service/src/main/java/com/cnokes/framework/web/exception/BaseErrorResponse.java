package com.cnokes.framework.web.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseErrorResponse {
	private String app;
	private String uri;
	private String timestamp;
	private String correlationId;
}