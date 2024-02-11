using System.Runtime.InteropServices.JavaScript;
using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class RichiestaIdrica
{
    public int id { get; set; }
    public Double quantita { get; set; }
    public int idColtivazione { get; set; }
    public int idBacino { get; set; }
    public String date { get; set; }
    public Approvazione approvato { get; set; }
    public String nomeAzienda { get; set; }

    public RichiestaIdrica()
    {
    }

    public RichiestaIdrica(Double quantita, int idColtivazione, int idBacino, String date, string nomeAzienda)
    {
        this.id = 0;
        this.quantita = quantita;
        this.idColtivazione = idColtivazione;
        this.idBacino = idBacino;
        this.date = date;
        this.nomeAzienda = nomeAzienda;
    }

    [JsonConstructor]
    public RichiestaIdrica(int id, double quantita, int idColtivazione, int idBacino, string date, string nomeAzienda,
        Approvazione approvato)
    {
        this.id = id;
        this.quantita = quantita;
        this.idColtivazione = idColtivazione;
        this.idBacino = idBacino;
        this.date = date;
        this.nomeAzienda = nomeAzienda;
        this.approvato = approvato;
    }


    public DateTime RichiestaAsDateTime()
    {
        DateTime date;
        if (DateTime.TryParse(this.date, out date))
        {
            return date;
        }
        else
        {
            return DateTime.MinValue;
        }
    }
}