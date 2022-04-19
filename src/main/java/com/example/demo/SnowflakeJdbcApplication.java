package com.example.demo;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SnowflakeJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnowflakeJdbcApplication.class, args);
	}

	@Bean
	public IParser jsonParser()
	{
		IParser fhirParser = getFhirContext().newJsonParser();
		fhirParser.setOmitResourceId(false);
		fhirParser.setOverrideResourceIdWithBundleEntryFullUrl(false);
		return fhirParser;
	}

	@Bean
	public FhirContext getFhirContext()
	{
		return FhirContext.forR4();
	}
}
