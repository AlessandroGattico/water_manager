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
	
	
	public Attivazione(int id, String data, int stato, int idAttuatore) {
		this.id = id;
		this.time = data;
		if (stato > 0) {
			this.current = true;
		} else {
			this.current = false;
		}
		this.idAttuatore = idAttuatore;
	}
	
}
