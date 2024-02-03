namespace WaterManagerUI.Model.cambio;

public class CambioDouble
{
    public int id { get; set; }
    public double newDouble { get; set; }
    public String property { get; set; }

    public CambioDouble(int id, double newDouble, string property)
    {
        this.id = id;
        this.newDouble = newDouble;
        this.property = property;
    }
}