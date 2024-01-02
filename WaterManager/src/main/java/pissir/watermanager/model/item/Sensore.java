package pissir.watermanager.model.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

/**
 * @author Alessandro Gattico
 */
@Setter
@Getter
@NoArgsConstructor
public class Sensore {
	
	private int id;
	private String nome;
	private String type;
	private HashSet<Misura> misure;
	private int idCampo;
	
	
	public Sensore (int id, String nome, String type, int idCampo) {
		this.id = id;
		this.nome = nome;
		this.type = type;
		this.misure = new HashSet<>();
		this.idCampo = idCampo;
	}
	
}
