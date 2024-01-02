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
	
}
