package pissir.watermanager.model.user;

import pissir.watermanager.model.item.*;
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
public class GestoreAzienda extends UserProfile {
	
	private Azienda azienda;
	private HashSet<RichiestaIdrica> richieste;
	
	
	public GestoreAzienda (int id, String nome, String cognome, String username, String mail, String password) {
		super(id, nome, cognome, username, mail, password, UserRole.GESTOREAZIENDA);
		this.richieste = new HashSet<>();
		this.azienda = null;
	}
	
}
