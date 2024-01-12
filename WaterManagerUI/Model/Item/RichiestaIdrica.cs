using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class RichiestaIdrica
{
    public int id { get; set; }
    public Double quantita { get; set; }
    public int idColtivazione { get; set; }
    public int idBacino { get; set; }
    public String date { get; set; }


    public RichiestaIdrica(Double quantita, int idColtivazione, int idBacino, String date)
    {
        this.id = 0;
        this.quantita = quantita;
        this.idColtivazione = idColtivazione;
        this.idBacino = idBacino;
        this.date = date;
    }

    [JsonConstructor]
    public RichiestaIdrica(int id, double quantita, int idColtivazione, int idBacino, string date)
    {
        this.id = id;
        this.quantita = quantita;
        this.idColtivazione = idColtivazione;
        this.idBacino = idBacino;
        this.date = date;
    }
}