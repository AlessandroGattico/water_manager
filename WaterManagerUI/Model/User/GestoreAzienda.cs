using WaterManagerUI.Model.Item;

namespace UserInterfaceWaterManager.Model.User
{
    public class GestoreAzienda : UserProfile
    {
        public Azienda azienda { get; set; }


        public GestoreAzienda(int id, String nome, String cognome, String username, String mail, String password,
            Azienda azienda) : base(id, nome, cognome, username, mail, password, UserRole.GESTOREAZIENDA)
        {
            this.azienda = azienda;
        }
    }
}