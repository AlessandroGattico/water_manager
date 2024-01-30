namespace WaterManagerUI.Model.cambio;

public class CambioString
{
    public int id { get; set; }
    public String newString { get; set; }
    public String property { get; set; }

    public CambioString(int id, string newString, string property)
    {
        this.id = id;
        this.newString = newString;
        this.property = property;
    }
}