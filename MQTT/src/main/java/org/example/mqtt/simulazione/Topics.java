package org.example.mqtt.simulazione;

import lombok.Getter;
import lombok.Setter;
import org.example.mqtt.simulazione.TopicCreator;

import java.util.LinkedList;


/**
 * @author Alessandro Gattico
 */

@Getter
@Setter

public class Topics {
	
	LinkedList<TopicCreator> topics;
	public Topics(){
		this.topics = new LinkedList<>();
	}
	
	public void addTopic(TopicCreator topic) {
		this.topics.add(topic);
	}
	
}
