package pissir.watermanager.model.user;


import com.watermanager.model.item.BacinoIdrico;
import com.watermanager.model.item.RichiestaIdrica;
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
	private HashSet<RichiestaIdrica> richieste;
	
	
	public GestoreIdrico (int id, String nome, String cognome, String username, String mail, String password) {
		super(id, nome, cognome, username, mail, password, UserRole.GESTOREIDRICO);
		this.richieste = new HashSet<>();
		this.bacinoIdrico = null;
	}
	
}