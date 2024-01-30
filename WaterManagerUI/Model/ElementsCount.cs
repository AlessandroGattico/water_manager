using Newtonsoft.Json;

namespace WaterManagerUI.Model;

public class ElementsCount
{
    public int gestoriAzienda { get; set; }
    public int gestoriBacino { get; set; }
    public int raccolti { get; set; }
    public int esigenze { get; set; }
    public int irrigazioni { get; set; }
    public int sensorTypes { get; set; }
    public int aziende { get; set; }
    public int campagne { get; set; }
    public int campi { get; set; }
    public int bacini { get; set; }


    [JsonConstructor]
    public ElementsCount(int gestoriAzienda, int gestoriBacino, int raccolti, int esigenze, int irrigazioni,
        int sensorTypes, int aziende, int campagne, int campi, int bacini)
    {
        this.gestoriAzienda = gestoriAzienda;
        this.gestoriBacino = gestoriBacino;
        this.raccolti = raccolti;
        this.esigenze = esigenze;
        this.irrigazioni = irrigazioni;
        this.sensorTypes = sensorTypes;
        this.aziende = aziende;
        this.campagne = campagne;
        this.campi = campi;
        this.bacini = bacini;
    }
}