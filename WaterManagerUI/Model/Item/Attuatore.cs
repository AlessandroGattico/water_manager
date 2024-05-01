using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class Attuatore
{
    public int id { get; set; }
    public String nome { get; set; }
    public int idCampo { get; set; }
    public HashSet<Attivazione> attivazioni { get; set; }

    public Attuatore()
    {
    }

    public Attuatore(String nome, int idCampo)
    {
        this.id = 0;
        this.nome = nome;
        this.idCampo = idCampo;
        this.attivazioni = new HashSet<Attivazione>();
    }

    [JsonConstructor]
    public Attuatore(int id, string nome, int idCampo, HashSet<Attivazione> attivazioni)
    {
        this.id = id;
        this.nome = nome;
        this.idCampo = idCampo;
        this.attivazioni = attivazioni;
    }


    public List<Attivazione> storicoAttivazioni()
    {
        if (this.attivazioni.Count == 0)
        {
            return new List<Attivazione>();
        }

        return attivazioni.OrderByDescending(c => c.attivazioneAsDateTime()).ToList();
    }
}