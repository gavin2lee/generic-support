package com.generic.support.netio.bootstrap;

import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvPrinter {
	private static final Logger log = LoggerFactory.getLogger(EnvPrinter.class);

	public static void main(String[] args) {
		Map<String, String> envs = System.getenv();

		log.info("======   environments  ======");
		for (Entry<String, String> env : envs.entrySet()) {
			log.info(String.format("%s:%s", env.getKey(), env.getValue()));
		}

		log.info("======   system properties  ======");
		Properties sysProps = System.getProperties();

		Enumeration<?> propNames = sysProps.propertyNames();
		while (propNames.hasMoreElements()) {
			log.info(String.format("%s:%s", (String) propNames.nextElement(),
					System.getProperty((String) propNames.nextElement())));
		}
	}

}
