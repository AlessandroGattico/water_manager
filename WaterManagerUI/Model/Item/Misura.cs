using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class Misura
{
    public int id { get; set; }
    public Double value { get; set; }
    public String time { get; set; }
    public int idSensore { get; set; }


    public Misura(Double value, String time, int idSensore)
    {
        this.id = 0;
        this.value = value;
        this.time = time;
        this.idSensore = idSensore;
    }

    [JsonConstructor]
    public Misura(int id, double value, string time, int idSensore)
    {
        this.id = id;
        this.value = value;
        this.time = time;
        this.idSensore = idSensore;
    }
}