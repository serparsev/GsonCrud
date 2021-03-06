package com.netmind.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.netmind.business.StudentBlImpl;

public class Config {

	static Logger logger = Logger.getLogger(Config.class);
	static Properties prop = null;
	static InputStream input = null;

	static {
		prop = new Properties();
		try {
			input = StudentBlImpl.class
					.getResourceAsStream(Constants.PROPERTIES_FILE);
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static String getTxtFileName() {
		return prop.getProperty(Constants.TXT_FILE_NAME);
	}

	public static String getJsonFileName() {
		return prop.getProperty(Constants.JSON_FILE_NAME);
	}

	public static String getTxtText() {
		return prop.getProperty(Constants.TXT_FILE_CREATED);
	}

	public static String getJsonText() {
		return prop.getProperty(Constants.JSON_FILE_CREATED);
	}
}
