package pissir.watermanager.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Alessandro Gattico
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
	
	
	public UserProfile (int id, String nome, String cognome, String username, String mail, String password,
						UserRole role) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.mail = mail;
		this.password = password;
		this.role = role;
	}
	
}
