package pissir.watermanager.model.user;

import pissir.watermanager.model.item.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

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
	
	
	public GestoreAzienda (int id, String nome, String cognome, String username, String mail, String password) {
		super(id, nome, cognome, username, mail, password, UserRole.GESTOREAZIENDA);
		this.azienda = null;
	}
	
}
