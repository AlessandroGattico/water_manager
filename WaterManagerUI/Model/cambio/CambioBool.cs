namespace WaterManagerUI.Model.cambio;

public class CambioBool
{
    public int id { get; set; }
    public bool newBool { get; set; }
    public String property { get; set; }

    public CambioBool(int id, bool newBool, string property)
    {
        this.id = id;
        this.newBool = newBool;
        this.property = property;
    }
}