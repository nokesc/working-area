package com.cnokes.infra_app.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cnokes.infra_app.network.repo.MongoNetworkPlanRepo;
import com.cnokes.infra_app.network.repo.TestDataSetup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	MongoNetworkPlanRepo mongoNetworkPlanRepo;

	@Autowired
	TestDataSetup testDataSetup;

	@GetMapping(path = "/headers")
	public Map<String, List<String>> headers(HttpServletRequest request) {
		request = WebUtils.getNativeRequest(request, org.apache.catalina.connector.RequestFacade.class);
		Map<String, List<String>> response = new LinkedHashMap<String, List<String>>();
		Enumeration<String> headerNamesEnum = request.getHeaderNames();
		List<String> headerNames = new ArrayList<>();
		while (headerNamesEnum.hasMoreElements()) {
			headerNames.add(headerNamesEnum.nextElement());
		}
		Collections.sort(headerNames);
		for (String headerName : headerNames) {
			Enumeration<String> headers = request.getHeaders(headerName);
			List<String> values = new ArrayList<>();
			response.put(headerName, values);
			while (headers.hasMoreElements()) {
				values.add(headers.nextElement());
			}
		}
		LOGGER.info(response.toString());
		return response;
	}

	@GetMapping(path = "/reset-data")
	public void resetData() {
		mongoNetworkPlanRepo.deleteAll();
		testDataSetup.setup();
	}
}