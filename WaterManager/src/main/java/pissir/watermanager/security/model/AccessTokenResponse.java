package pissir.watermanager.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Getter
@Setter
public class AccessTokenResponse {
	@JsonProperty("access_token")
	private String accessToken;
}
