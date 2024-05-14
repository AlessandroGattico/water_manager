package pissir.watermanager.mqtt.simulazione;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;


/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Getter
@Setter

public class Topics {
	
	LinkedList<TopicCreator> topics;
	
	
	public Topics() {
		this.topics = new LinkedList<>();
	}
	
}
