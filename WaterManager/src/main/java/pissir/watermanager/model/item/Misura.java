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
public class Misura {
	
	private int id;
	private Double value;
	private String time;
	private int idSensore;
	
	
	public Misura (int id, Double value, String time, int idSensore) {
		this.id = id;
		this.value = value;
		this.time = time;
		this.idSensore = idSensore;
	}
	
	
	public Misura(Double value, String time, int idSensore) {
		this.value = value;
		this.time = time;
		this.idSensore = idSensore;
	}
	
}
