package pissir.watermanager.model.item;

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
@NoArgsConstructor
public class Approvazione {
	
	private int id;
	private int idRichiesta;
	private int idGestore;
	private boolean approvato;
	private String date;
	
	
	public Approvazione(int id, int idRichiesta, int idGestore, boolean approvato,
						String date) {
		this.id = id;
		this.idRichiesta = idRichiesta;
		this.idGestore = idGestore;
		this.approvato = approvato;
		this.date = date;
	}
	
}
