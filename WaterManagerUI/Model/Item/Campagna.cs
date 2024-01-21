using Newtonsoft.Json;

namespace WaterManagerUI.Model.Item;

public class Campagna
{
    public int id { get; set; }
    public String nome { get; set; }
    public HashSet<Campo> campi { get; set; }
    public int idAzienda { get; set; }


    public Campagna()
    {
        this.campi = new HashSet<Campo>();
    }

    public Campagna(String nome, HashSet<Campo> campi, int idAzienda)
    {
        this.nome = nome;
        this.campi = campi;
        this.idAzienda = idAzienda;
        this.campi = campi;
    }


    public Campagna(int id, String nome, int idAzienda)
    {
        this.id = id;
        this.nome = nome;
        this.idAzienda = idAzienda;
        this.campi = new HashSet<Campo>();
    }

    [JsonConstructor]
    public Campagna(int id, string nome, HashSet<Campo> campi, int idAzienda)
    {
        this.id = id;
        this.nome = nome;
        this.campi = campi;
        this.idAzienda = idAzienda;
    }

    public Double size()
    {
        Double size = 0;


        if (campi.Count > 0)
        {
            foreach (Campo campo in campi)
            {
                size += campo.dimensione;
            }
        }

        return size;
    }
}