package org.example.mqtt.simulazione;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
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
		this.topic = "SENSOR/" + typeSensore + "/" + idAzienda + "/" + idCampagna + "/" + idCampo +
				"/" + nomeSensore;
	}
	
}
