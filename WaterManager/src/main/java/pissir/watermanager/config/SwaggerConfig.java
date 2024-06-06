package pissir.watermanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
	
	@Bean
	public GroupedOpenApi aziendaApi() {
		return GroupedOpenApi.builder()
				.group("Azienda")
				.pathsToMatch("/api/v1/azienda/**", "^((?!/api/v1/azienda/campo/).)*$",
						"^((?!/api/v1/azienda/campagna/).)*$", "^((?!/api/v1/azienda/coltivazione/).)*$",
						"^((?!/api/v1/azienda/sensore/).)*$", "^((?!/api/v1/azienda/attuatore/).)*$")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi.builder()
				.group("Admin")
				.pathsToMatch("/api/v1/admin/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi approvazioneApi() {
		return GroupedOpenApi.builder()
				.group("Approvazione")
				.pathsToMatch("/api/v1/bacino/approvazione/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi attuatoreApi() {
		return GroupedOpenApi.builder()
				.group("Attuatore")
				.pathsToMatch("/api/v1/azienda/attuatore/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi attivazioneApi() {
		return GroupedOpenApi.builder()
				.group("Attivazione")
				.pathsToMatch("/api/v1/azienda/attivazione/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi campagnaApi() {
		return GroupedOpenApi.builder()
				.group("Campagna")
				.pathsToMatch("/api/v1/azienda/campagna/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi campoApi() {
		return GroupedOpenApi.builder()
				.group("Campo")
				.pathsToMatch("/api/v1/azienda/campo/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi coltivazioneApi() {
		return GroupedOpenApi.builder()
				.group("Coltivazione")
				.pathsToMatch("/api/v1/azienda/coltivazione/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi bacinoApi() {
		return GroupedOpenApi.builder()
				.group("Bacino idrico")
				.pathsToMatch("/api/v1/bacino/**", "^((?!/api/v1/bacino/approvazione/).)*$")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi misuraApi() {
		return GroupedOpenApi.builder()
				.group("Misura")
				.pathsToMatch("/api/v1/misura/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi richiestaApi() {
		return GroupedOpenApi.builder()
				.group("Richiesta idrica")
				.pathsToMatch("/api/v1/richiesta/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi risorsaAziendaApi() {
		return GroupedOpenApi.builder()
				.group("Risorsa idrica azienda")
				.pathsToMatch("/api/v1/risorsa/azienda/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi risorsaBacinoApi() {
		return GroupedOpenApi.builder()
				.group("Risorsa idrica bacino idrico")
				.pathsToMatch("/api/v1/risorsa/bacino/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi sensoreApi() {
		return GroupedOpenApi.builder()
				.group("Sensore")
				.pathsToMatch("/api/v1/azienda/sensore/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder()
				.group("User")
				.pathsToMatch("/api/v1/user/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi utilsApi() {
		return GroupedOpenApi.builder()
				.group("Utils")
				.pathsToMatch("/api/v1/utils/**")
				.packagesToScan("pissir.watermanager.controller")
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi authApi() {
		return GroupedOpenApi.builder()
				.group("Risorsa idrica azienda")
				.pathsToMatch("/api/v1/auth/**")
				.packagesToScan("pissir.watermanager.security.controller")
				.build();
	}
	
	
}
