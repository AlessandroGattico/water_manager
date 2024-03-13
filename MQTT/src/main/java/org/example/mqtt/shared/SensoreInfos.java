package org.example.mqtt.shared;

import lombok.Getter;
import lombok.Setter;
import org.example.mqtt.model.Sensore;

import java.util.List;
import java.util.Map;

@Getter
public enum SensoreInfos {
	INSTANCE;
	private Map<String, List<Sensore>> sensoreClassificationByType;
}
