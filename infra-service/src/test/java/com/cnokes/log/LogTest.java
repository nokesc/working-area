package com.cnokes.log;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogTest.class);

	@Test
	public void a() {
		LOGGER.info("test");
	}

	@Test
	public void b() {
		Logger LOGGER = LoggerFactory.getLogger("mylogger\"abc");
		LOGGER.info("test");
	}
}
