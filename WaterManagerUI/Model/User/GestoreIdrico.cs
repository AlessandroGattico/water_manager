using System;
using WaterManagerUI.Model.Item;

namespace UserInterfaceWaterManager.Model.User
{
    public class GestoreIdrico : UserProfile
    {
        public BacinoIdrico bacinoIdrico { get; set; }
        public HashSet<RisorsaIdrica> risorse { get; set; }

        public GestoreIdrico(int id, String nome, String cognome, String username, String mail, String password,
                             BacinoIdrico bacinoIdrico) : base(id, nome, cognome, username, mail, password, UserRole.GESTOREIDRICO)
        {
            this.bacinoIdrico = bacinoIdrico;
            this.risorse = new HashSet<RisorsaIdrica>();
        }

    }
}

