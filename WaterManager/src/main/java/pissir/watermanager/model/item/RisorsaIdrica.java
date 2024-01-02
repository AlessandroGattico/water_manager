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
public class RisorsaIdrica {
	
	private int id;
	private String data;
	private Double disponibilita;
	private Double consumo;
	private int idSource;
	
	
	public RisorsaIdrica (int id, String data, Double disponibilita, Double consumo, int idSource) {
		this.id = id;
		this.data = data;
		this.disponibilita = disponibilita;
		this.consumo = consumo;
		this.idSource = idSource;
	}
	
}
