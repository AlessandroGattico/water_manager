using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class Sensore
{
    public int id { get; set; }
    public String nome { get; set; }
    public String type { get; set; }
    public HashSet<Misura> misure { get; set; }
    public int idCampo { get; set; }

    public Sensore()
    {
    }

    public Sensore(String nome, String type, int idCampo)
    {
        this.id = 0;
        this.nome = nome;
        this.type = type;
        this.misure = new HashSet<Misura>();
        this.idCampo = idCampo;
    }

    [JsonConstructor]
    public Sensore(int id, string nome, string type, HashSet<Misura> misure, int idCampo)
    {
        this.id = id;
        this.nome = nome;
        this.type = type;
        this.misure = misure;
        this.idCampo = idCampo;
    }
}