package com.cnokes.framework.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(0)
public class TraceFilter extends GenericFilter {

	private static final long serialVersionUID = 1L;

	private static final String X_REQUEST_ID = "x-request-id";

	public static String getRequestId() {
		return MDC.get(X_REQUEST_ID);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestId = getOrGenerateRequestId((HttpServletRequest) request);
		MDC.put(X_REQUEST_ID, requestId);
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader(X_REQUEST_ID, requestId);
		try {
			chain.doFilter(request, response);
		} finally {
			MDC.remove(X_REQUEST_ID);
		}
	}

	protected String getOrGenerateRequestId(HttpServletRequest request) {
		String requestId = request.getHeader(X_REQUEST_ID);
		if (!StringUtils.hasText(requestId)) {
			requestId = UUID.randomUUID().toString();
		}
		return requestId;
	}
}