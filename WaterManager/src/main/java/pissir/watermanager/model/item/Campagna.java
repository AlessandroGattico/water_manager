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
public class Campagna {
	
	private int id;
	private String nome;
	private HashSet<Campo> campi;
	private int idAzienda;
	
	
	public Campagna (int id, String nome, int idAzienda) {
		this.id = id;
		this.nome = nome;
		this.idAzienda = idAzienda;
		this.campi = new HashSet<>();
	}
	
}
