package pissir.watermanager.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubUser {
	private String id;
	private String login;
	private String name;
	private String email;
	private String avatarUrl;
	private String profileUrl;
	
	public GithubUser() {
	}
}
