namespace WaterManagerUI.Model.Item;

public class Azienda
{
    public int id { get; set; }
    public String nome { get; set; }
    public HashSet<Campagna> campagne { get; set; }
    public HashSet<RisorsaIdrica> risorse { get; set; }
    public int idGestore { get; set; }


    public Azienda(String nome, int idGestore)
    {
        this.id = 0;
        this.nome = nome;
        this.idGestore = idGestore;
        this.campagne = new HashSet<Campagna>();
        this.risorse = new HashSet<RisorsaIdrica>();
    }

    public Azienda(int id, string nome, HashSet<Campagna> campagne, HashSet<RisorsaIdrica> risorse, int idGestore)
    {
        this.id = id;
        this.nome = nome;
        this.campagne = campagne;
        this.risorse = risorse;
        this.idGestore = idGestore;
    }
}