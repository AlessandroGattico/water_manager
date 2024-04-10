package pissir.watermanager.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
public class UserProfile {
	
	private int id;
	private String nome;
	private String cognome;
	private String username;
	private String mail;
	private String password;
	private UserRole role;
	private boolean enabled;
	
	
	public UserProfile(int id, String nome, String cognome, String username, String mail, String password,
					   UserRole role, boolean enabled) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.mail = mail;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}
	
	
	public UserProfile(String nome, String cognome, String username, String mail, String password, UserRole role,
					   boolean enabled) {
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.mail = mail;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}
	
}
