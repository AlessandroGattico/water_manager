namespace WaterManagerUI.Model.Item;

public class Approvazione
{
    public int id { get; set; }
    public int idRichiesta { get; set; }
    public int idGestore { get; set; }
    public bool approvato { get; set; }
    public String date { get; set; }

    public Approvazione()
    {
    }

    public Approvazione(int id, int idRichiesta, int idGestore, bool approvato, string date)
    {
        this.id = id;
        this.idRichiesta = idRichiesta;
        this.idGestore = idGestore;
        this.approvato = approvato;
        this.date = date;
    }
}