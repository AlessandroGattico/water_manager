namespace WaterManagerUI.Model.cambio;

public class CambioInt
{
    public int id { get; set; }
    public int newInt { get; set; }
    public String property { get; set; }


    public CambioInt(int id, int newInt, string property)
    {
        this.id = id;
        this.newInt = newInt;
        this.property = property;
    }
}