namespace WaterManagerUI.Model.Item;

public class Attivazione
{
    public int id { get; set; }
    public bool current { get; set; }
    public String time { get; set; }
    public int idAttuatore { get; set; }


    public Attivazione(String data, bool stato, int idAttuatore)
    {
        this.id = 0;
        this.time = data;
        this.current = stato;
        this.idAttuatore = idAttuatore;
    }

    public Attivazione(int id, bool current, string time, int idAttuatore)
    {
        this.id = id;
        this.current = current;
        this.time = time;
        this.idAttuatore = idAttuatore;
    }
}