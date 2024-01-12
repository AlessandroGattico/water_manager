using System;
namespace UserInterfaceWaterManager.Model.User
{
    public class Admin : UserProfile
    {
        public HashSet<GestoreAzienda> gestoriAziende { get; set; }
        public HashSet<GestoreIdrico> gestoriIdrici { get; set; }
        

        public Admin(int id, String nome, String cognome, String username, String mail, String password) : base(id, nome, cognome, username, mail, password, UserRole.SYSTEMADMIN)
        {
            this.gestoriAziende = new HashSet<GestoreAzienda>();
            this.gestoriIdrici = new HashSet<GestoreIdrico>();
        }

    }
}

