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
public class Attuatore {
	
	private int id;
	private String nome;
	private int idCampo;
	private HashSet<Attivazione> attivazioni;
	
	
	public Attuatore (int id, String nome, int idCampo) {
		this.id = id;
		this.nome = nome;
		this.idCampo = idCampo;
		this.attivazioni = new HashSet<>();
	}
	
}
