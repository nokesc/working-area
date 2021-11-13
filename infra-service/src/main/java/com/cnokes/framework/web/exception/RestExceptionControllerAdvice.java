package com.cnokes.framework.web.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionControllerAdvice {

	@Autowired
	HttpServletRequest request;

	public <T extends BaseErrorResponse> T populate(T baseErrorResponse) {
		baseErrorResponse.setTimestamp(LocalDateTime.now().toString());
		baseErrorResponse.setUri(request.getRequestURI());

		// TODO
		baseErrorResponse.setApp(null);
		baseErrorResponse.setCorrelationId(null);
		return baseErrorResponse;
	}

	@ExceptionHandler()
	public ResponseEntity<BadRequest> handle(HttpBadRequestException ex) {
		BadRequest badRequest = populate(new BadRequest());
		if (ex.getFieldErrors() != null) {
			badRequest.setFieldErrors(ex.getFieldErrors());
		}
		ResponseEntity<BadRequest> re = ResponseEntity.badRequest().body(badRequest);
		return re;
	}

	@ExceptionHandler()
	public ResponseEntity<BaseErrorResponse> handle(HttpNotFoundException ex) {
		BaseErrorResponse badRequest = populate(new BaseErrorResponse());		
		ResponseEntity<BaseErrorResponse> re = ResponseEntity.status(HttpStatus.NOT_FOUND).body(badRequest);
		return re;
	}
}
