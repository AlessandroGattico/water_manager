package pissir.watermanager.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {
	@JsonProperty("access_token")
	private String accessToken;
	
	// Getter e setter
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}