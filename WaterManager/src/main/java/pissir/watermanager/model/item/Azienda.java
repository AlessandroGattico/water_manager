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
public class Azienda {
	
	private int id;
	private String nome;
	private HashSet<Campagna> campagne;
	private HashSet<RisorsaIdrica> risorse;
	private int idGestore;
	
	
	public Azienda (int id, String nome, int idGestore) {
		this.id = id;
		this.nome = nome;
		this.idGestore = idGestore;
		this.campagne = new HashSet<>();
		this.risorse = new HashSet<>();
	}
	
}
