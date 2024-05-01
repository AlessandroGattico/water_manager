using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class RisorsaIdrica
{
    public int id { get; set; }
    public String data { get; set; }
    public Double disponibilita { get; set; }
    public Double consumo { get; set; }
    public int idSource { get; set; }

    public RisorsaIdrica()
    {
    }

    public RisorsaIdrica(String data, Double disponibilita, Double consumo, int idSource)
    {
        this.id = 0;
        this.data = data;
        this.disponibilita = disponibilita;
        this.consumo = consumo;
        this.idSource = idSource;
    }

    [JsonConstructor]
    public RisorsaIdrica(int id, string data, double disponibilita, double consumo, int idSource)
    {
        this.id = id;
        this.data = data;
        this.disponibilita = disponibilita;
        this.consumo = consumo;
        this.idSource = idSource;
    }
    
    public DateTime RisorsaAsDateTime()
    {
        DateTime seminaDate;
        
        if (DateTime.TryParse(data, out seminaDate))
        {
            return seminaDate;
        }
        else
        {
            return DateTime.MinValue;
        }
    }
}