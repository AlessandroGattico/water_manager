package pissir.watermanager.model.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pissir.watermanager.model.item.BacinoIdrico;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Setter
@Getter
@NoArgsConstructor
public class GestoreIdrico extends UserProfile {
	
	private BacinoIdrico bacinoIdrico;
	
	
	public GestoreIdrico(int id, String nome, String cognome, String username, String mail, String password,
						 boolean enabled) {
		super(id, nome, cognome, username, mail, password, UserRole.GESTOREIDRICO, enabled);
		this.bacinoIdrico = null;
	}
	
}