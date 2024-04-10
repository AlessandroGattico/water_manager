package pissir.watermanager.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pissir.watermanager.model.item.Azienda;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Setter
@Getter
@NoArgsConstructor
public class GestoreAzienda extends UserProfile {
	
	private Azienda azienda;
	
	
	public GestoreAzienda(int id, String nome, String cognome, String username, String mail, String password,
						  boolean enabled) {
		super(id, nome, cognome, username, mail, password, UserRole.GESTOREAZIENDA, enabled);
		this.azienda = null;
	}
	
}
