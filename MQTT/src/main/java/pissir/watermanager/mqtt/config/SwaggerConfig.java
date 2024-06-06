package pissir.watermanager.mqtt.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Configuration
public class SwaggerConfig {
	
	@Bean
	public GroupedOpenApi mqttApi() {
		return GroupedOpenApi.builder()
				.group("Mqtt")
				.pathsToMatch("/api/v1/MQTT/**")
				.build();
	}
	
}




