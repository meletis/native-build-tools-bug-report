/*
 * Copyright (C) 2012-23 Dobility, Inc.
 *
 * All rights reserved.
 */

package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({MyAppProperties.class})
public class MyApplication {

	public static void main(String[] args) {
		// Set the JVM timezone to UTC
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		// Set the default locale to be US.
		Locale.setDefault(Locale.US);

		// Start the app
		SpringApplication.run(MyApplication.class, args);
	}
}
