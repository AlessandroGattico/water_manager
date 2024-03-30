package pissir.watermanager.model.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Setter
@Getter
@NoArgsConstructor
public class Coltivazione {
	
	private int id;
	private String raccolto;
	private String irrigazione;
	private String esigenza;
	private Double temperatura;
	private Double umidita;
	private String semina;
	private int idCampo;
	
	
	public Coltivazione (int id, String raccolto, String irrigazione, String esigenza, Double temperatura,
						 Double umidita, String semina, int idCampo) {
		this.id = id;
		this.raccolto = raccolto;
		this.irrigazione = irrigazione;
		this.esigenza = esigenza;
		this.temperatura = temperatura;
		this.umidita = umidita;
		this.semina = semina;
		this.idCampo = idCampo;
	}
	
}
