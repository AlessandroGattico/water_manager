package org.example.mqtt.model;

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
public class Attivazione {
	
	private int id;
	private boolean current;
	private String time;
	private int idAttuatore;
	
	
	public Attivazione(boolean current, String time, int idAttuatore) {
		this.current = current;
		this.time = time;
		this.idAttuatore = idAttuatore;
	}
	
	
	public Attivazione(int id, String data, boolean stato, int idAttuatore) {
		this.id = id;
		this.time = data;
		this.current = stato;
		this.idAttuatore = idAttuatore;
	}
	
}
