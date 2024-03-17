package pissir.watermanager.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

/**
 * @author Alessandro Gattico
 */
@Setter
@Getter
@NoArgsConstructor
public class Admin extends UserProfile {
	
	private HashSet<GestoreAzienda> gestoriAziende;
	private HashSet<GestoreIdrico> gestoriIdrici;
	
	
	public Admin(int id, String nome, String cognome, String username, String mail, String password) {
		super(id, nome, cognome, username, mail, password, UserRole.SYSTEMADMIN);
		this.gestoriAziende = new HashSet<>();
		this.gestoriIdrici = new HashSet<>();
	}
	
}
