package pissir.watermanager.security.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pissir.watermanager.model.user.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
	
	private String nome;
	private String cognome;
	private String username;
	private String mail;
	private String password;
	private int role;
}
