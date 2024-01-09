namespace WaterManagerUI.Model.Item;

public class Coltivazione
{
    public int id { get; set; }
    public String raccolto { get; set; }
    public String irrigazione { get; set; }
    public String esigenza { get; set; }
    public Double temperatura { get; set; }
    public Double umidita { get; set; }
    public String semina { get; set; }
    public int idCampo { get; set; }


    public Coltivazione(String raccolto, String irrigazione, String esigenza, Double temperatura,
        Double umidita, String semina, int idCampo)
    {
        this.id = 0;
        this.raccolto = raccolto;
        this.irrigazione = irrigazione;
        this.esigenza = esigenza;
        this.temperatura = temperatura;
        this.umidita = umidita;
        this.semina = semina;
        this.idCampo = idCampo;
    }

    public Coltivazione(int id, string raccolto, string irrigazione, string esigenza, double temperatura,
        double umidita, string semina, int idCampo)
    {
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