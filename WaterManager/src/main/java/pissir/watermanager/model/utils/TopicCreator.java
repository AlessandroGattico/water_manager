package pissir.watermanager.model.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
		this.topic = "sensore/" + idAzienda + "/" + idCampagna + "/" + idCampo +
				"/" + nomeSensore;
	}
	
}
