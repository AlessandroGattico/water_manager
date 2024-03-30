package pissir.watermanager.model.item;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Setter
@Getter
@NoArgsConstructor
public class BacinoIdrico {
	
	private int id;
	private String nome;
	private HashSet<RisorsaIdrica> risorse;
	private int idGestore;
	private HashSet<RichiestaIdrica> richieste;
	
	
	public BacinoIdrico(int id, String nome, int idGestore) {
		this.id = id;
		this.nome = nome;
		this.idGestore = idGestore;
		this.risorse = new HashSet<>();
		this.richieste = new HashSet<>();
	}
	
}
