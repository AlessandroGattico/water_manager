package pissir.watermanager.model.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Alessandro Gattico
 */
@Setter
@Getter
@NoArgsConstructor
public class RichiestaIdrica {
	
	private int id;
	private Double quantita;
	private int idColtivazione;
	private int idBacino;
	private String date;
	
	
	public RichiestaIdrica (int id, Double quantita, int idColtivazione, int idBacino, String date) {
		this.id = id;
		this.quantita = quantita;
		this.idColtivazione = idColtivazione;
		this.idBacino = idBacino;
		this.date = date;
	}
	
}
