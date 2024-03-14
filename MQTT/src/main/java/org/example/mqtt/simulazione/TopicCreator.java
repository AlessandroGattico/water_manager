package org.example.mqtt.simulazione;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Alessandro Gattico
 */

@NoArgsConstructor
public class TopicCreator {
	
	@Setter
	private int idAzienda;
	
	@Setter
	private int idCampagna;
	
	@Setter
	private int idCampo;
	
	@Setter
	private String nomeSensore;
	
	@Getter
	@Setter
	private int idSensore;
	
	@Getter
	@Setter
	private String typeSensore;
	
	@Getter
	private String topic;
	
	
	public void createTopic() {
		this.topic = typeSensore + "/" + idAzienda + "/" + idCampagna + "/" + idCampo +
				"/" + nomeSensore;
	}
	
}
