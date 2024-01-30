using Newtonsoft.Json;

namespace UserInterfaceWaterManager.Model.User
{
    public class Admin : UserProfile
    {
        public HashSet<GestoreAzienda> gestoriAziende { get; set; }
        public HashSet<GestoreIdrico> gestoriIdrici { get; set; }
        public HashSet<String> raccolti { get; set; }
        public HashSet<String> esigenze { get; set; }
        public HashSet<String> irrigazioni { get; set; }


        public Admin(int id, String nome, String cognome, String username, String mail, String password) : base(id,
            nome, cognome, username, mail, password, UserRole.SYSTEMADMIN)
        {
            this.gestoriAziende = new HashSet<GestoreAzienda>();
            this.gestoriIdrici = new HashSet<GestoreIdrico>();
            this.esigenze = new HashSet<String>();
            this.raccolti = new HashSet<String>();
            this.irrigazioni = new HashSet<String>();
        }

        [JsonConstructor]
        public Admin(int id, String nome, String cognome, String username, String mail, String password,
            HashSet<GestoreAzienda> gestoriAziende, HashSet<GestoreIdrico> gestoriIdrici) : base(id, nome, cognome,
            username, mail, password, UserRole.SYSTEMADMIN)
        {
            this.gestoriAziende = gestoriAziende;
            this.gestoriIdrici = gestoriIdrici;
            this.esigenze = new HashSet<String>();
            this.raccolti = new HashSet<String>();
            this.irrigazioni = new HashSet<String>();
        }
    }
}