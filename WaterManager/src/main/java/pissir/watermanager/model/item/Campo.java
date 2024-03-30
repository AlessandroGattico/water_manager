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
public class Campo {
	
	private int id;
	private String nome;
	private Double dimensione;
	private HashSet<Coltivazione> coltivazioni;
	private HashSet<Sensore> sensori;
	private HashSet<Attuatore> attuatori;
	private int idCampagna;
	
	
	public Campo (int id, String nome, Double dimensione, int idCampagna) {
		this.id = id;
		this.idCampagna = idCampagna;
		this.nome = nome;
		this.dimensione = dimensione;
		this.coltivazioni = new HashSet<>();
		this.sensori = new HashSet<>();
		this.attuatori = new HashSet<>();
	}
	
}
