package pissir.watermanager.model.user;


import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.item.RichiestaIdrica;
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
public class GestoreIdrico extends UserProfile {
	
	private BacinoIdrico bacinoIdrico;
	
	
	public GestoreIdrico (int id, String nome, String cognome, String username, String mail, String password) {
		super(id, nome, cognome, username, mail, password, UserRole.GESTOREIDRICO);
		this.bacinoIdrico = null;
	}
	
}