package org.example.mqtt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;


/**
 * @author Alessandro Gattico
 */

@Getter
@Setter
@NoArgsConstructor
public class Topics {
	
	LinkedList<TopicCreator> topics;
	
	
	public void addTopic(TopicCreator topic) {
		this.topics.add(topic);
	}
	
}
