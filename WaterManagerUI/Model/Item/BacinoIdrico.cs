using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class BacinoIdrico
{
    public int id { get; set; }
    public String nome { get; set; }
    public HashSet<RisorsaIdrica> risorse { get; set; }
    public int idGestore { get; set; }


    public BacinoIdrico(String nome, int idGestore)
    {
        this.id = 0;
        this.nome = nome;
        this.idGestore = idGestore;
        this.risorse = new HashSet<RisorsaIdrica>();
    }

    [JsonConstructor]
    public BacinoIdrico(int id, string nome, HashSet<RisorsaIdrica> risorse, int idGestore)
    {
        this.id = id;
        this.nome = nome;
        this.risorse = risorse;
        this.idGestore = idGestore;
    }
}