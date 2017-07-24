package io.hmheng.grading.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GradingLoggerFactory {
	
	public static GradingLogger getLogger(Class<?> clazz) {
		Logger parentLogger = LoggerFactory.getLogger(clazz);
		GradingLogger logger = new GradingLogger(parentLogger);
		return logger;
	}
	
	public static GradingLogger getLogger(String name) {
		Logger parentLogger = LoggerFactory.getLogger(name);
		GradingLogger logger = new GradingLogger(parentLogger);
		return logger;
	}

}