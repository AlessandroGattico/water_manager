package pissir.watermanager.mqtt.model;

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
	
	
	public Misura(Double value, String time, int idSensore) {
		this.value = value;
		this.time = time;
		this.idSensore = idSensore;
	}
	
}
