package com.cnokes.framework.web.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequest extends BaseErrorResponse {
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();
}