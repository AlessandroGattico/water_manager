using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class BacinoIdrico
{
    public int id { get; set; }
    public String nome { get; set; }
    public HashSet<RisorsaIdrica> risorse { get; set; }
    public HashSet<RichiestaIdrica> richieste { get; set; }
    public int idGestore { get; set; }

    public BacinoIdrico()
    {
        this.risorse = new HashSet<RisorsaIdrica>();
        this.richieste = new HashSet<RichiestaIdrica>();
    }

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
    
    public Double dispAttuale()
    {
        if (this.risorse.Count > 0)
        {
            return this.storicoRisorse().First().disponibilita;
        }
        else
        {
            return 0;
        }
    }
    
    public List<RisorsaIdrica> storicoRisorse()
    {
        return risorse.OrderByDescending(c => c.RisorsaAsDateTime()).ToList();
    }
    
    public List<RichiestaIdrica> storicoRichieste()
    {
        return richieste.OrderByDescending(c => c.RichiestaAsDateTime()).ToList();
    }


    public List<RichiestaIdrica> richiesteSospeso()
    {
        return this.richieste
            .Where(r => r.approvato == null)
            .OrderBy(r => r.RichiestaAsDateTime())
            .ToList();
    }

    public List<RichiestaIdrica> richiesteRifiutate()
    {
        return this.richieste
            .Where(r => r.approvato?.approvato == false)
            .OrderBy(r => r.RichiestaAsDateTime())
            .ToList();
    }

    public List<RichiestaIdrica> richiesteApprovate()
    {
        return this.richieste
            .Where(r => r.approvato?.approvato == true)
            .OrderBy(r => r.RichiestaAsDateTime())
            .ToList();
    }
}