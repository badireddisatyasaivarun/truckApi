package com.TruckApi.TruckApi.Properties;

import java.util.Collections;

import org.springframework.context.annotation.Bean;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class Swagger {

	@Bean
	public Docket swaggerConfiguration() {
		// return a prepared socket instance
		return new Docket(DocumentationType.SWAGGER_2).select().build().apiInfo(apiDetails());

	}

	private ApiInfo apiDetails() {

		return new ApiInfo("Truck's Api", "Api for the Trucks", "1.0", "Easy to use",
				new springfox.documentation.service.Contact("Pranav Gupta",
						"https://play.google.com/store/apps/details?id=com.chirag4601.new_truck_booking_app",
						"liveasy97@gmail.com"),
				"API License", "null", Collections.EMPTY_LIST);
	}

}
