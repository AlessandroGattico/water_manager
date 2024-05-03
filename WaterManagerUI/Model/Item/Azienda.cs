using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class Azienda
{
    public int id { get; set; }
    public String nome { get; set; }
    public HashSet<Campagna> campagne { get; set; }
    public HashSet<RisorsaIdrica> risorse { get; set; }
    public HashSet<RichiestaIdrica> richieste { get; set; }
    public int idGestore { get; set; }

    public Azienda()
    {
        this.campagne = new HashSet<Campagna>();
        this.risorse = new HashSet<RisorsaIdrica>();
        this.richieste = new HashSet<RichiestaIdrica>();
    }

    public Azienda(String nome, int idGestore)
    {
        this.id = 0;
        this.nome = nome;
        this.idGestore = idGestore;
        this.campagne = new HashSet<Campagna>();
        this.risorse = new HashSet<RisorsaIdrica>();
        this.richieste = new HashSet<RichiestaIdrica>();
    }

    [JsonConstructor]
    public Azienda(int id, string nome, HashSet<Campagna> campagne, HashSet<RisorsaIdrica> risorse,
        HashSet<RichiestaIdrica> richieste, int idGestore)
    {
        this.id = id;
        this.nome = nome;
        this.campagne = campagne;
        this.risorse = risorse;
        this.richieste = richieste;
        this.idGestore = idGestore;

        if (this.risorse == null)
        {
            this.risorse = new HashSet<RisorsaIdrica>();
        }

        if (this.campagne == null)
        {
            this.campagne = new HashSet<Campagna>();
        }

        if (this.richieste == null)
        {
            this.richieste = new HashSet<RichiestaIdrica>();
        }
    }


    public Double size()
    {
        Double size = 0;

        if (campagne.Any())
        {
            foreach (Campagna campagna in campagne)
            {
                size += campagna.size();
            }
        }

        return size;
    }

    public Double dispAttuale()
    {
        return this.storicoRisorse().FirstOrDefault().disponibilita;
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
        if (this.richieste.Count > 0)
        {
            List<RichiestaIdrica> richiesteNonApprovate = this.richieste
                .Where(r => r.approvato == null)
                .OrderBy(r => r.date)
                .ToList();

            return richiesteNonApprovate;
        }
        else
        {
            return new List<RichiestaIdrica>();
        }
    }


    public List<RichiestaIdrica> richiesteRifiutate()
    {
        if (this.richieste.Count > 0)
        {
            List<RichiestaIdrica> richiesteNonApprovate = this.richieste
                .Where(r => r.approvato?.approvato == false)
                .OrderBy(r => r.date)
                .ToList();

            return richiesteNonApprovate;
        }
        else
        {
            return new List<RichiestaIdrica>();
        }
    }

    public List<RichiestaIdrica> richiesteApprovate()
    {
        if (this.richieste.Count > 0)
        {
            List<RichiestaIdrica> richiesteNonApprovate = this.richieste
                .Where(r => r.approvato?.approvato == true)
                .OrderBy(r => r.date)
                .ToList();

            return richiesteNonApprovate;
        }
        else
        {
            return new List<RichiestaIdrica>();
        }
    }
}