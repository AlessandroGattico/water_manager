using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class Campo
{
    public int id { get; set; }
    public String nome { get; set; }
    public Double dimensione { get; set; }
    public HashSet<Coltivazione> coltivazioni { get; set; }
    public HashSet<Sensore> sensori { get; set; }
    public HashSet<Attuatore> attuatori { get; set; }
    public int idCampagna { get; set; }


    public Campo(String nome, Double dimensione, int idCampagna)
    {
        this.id = 0;
        this.idCampagna = idCampagna;
        this.nome = nome;
        this.dimensione = dimensione;
        this.coltivazioni = new HashSet<Coltivazione>();
        this.sensori = new HashSet<Sensore>();
        this.attuatori = new HashSet<Attuatore>();
    }

    [JsonConstructor]
    public Campo(int id, string nome, double dimensione, HashSet<Coltivazione> coltivazioni, HashSet<Sensore> sensori,
        HashSet<Attuatore> attuatori, int idCampagna)
    {
        this.id = id;
        this.nome = nome;
        this.dimensione = dimensione;
        this.coltivazioni = coltivazioni;
        this.sensori = sensori;
        this.attuatori = attuatori;
        this.idCampagna = idCampagna;
    }
}