package pissir.watermanager.model.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public void addTopic(TopicCreator topic){
		this.topics.add(topic);
	}
}
