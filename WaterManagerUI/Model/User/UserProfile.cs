using System;
using Microsoft.AspNetCore.Identity;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace UserInterfaceWaterManager.Model.User
{
    public class UserProfile
    {
        public int id { get; set; }
        public String nome { get; set; }
        public String cognome { get; set; }
        public String username { get; set; }
        public String mail { get; set; }
        public String password { get; set; }
        public UserRole userRole { get; set; }

        public UserProfile(String nome, String cognome, String username, String mail, String password,
            UserRole userRole)
        {
            this.nome = nome;
            this.cognome = cognome;
            this.username = username;
            this.mail = mail;
            this.password = password;
            this.userRole = userRole;
        }


        [JsonConstructor]
        public UserProfile(int id, String nome, String cognome, String username, String mail, String password,
            UserRole userRole)
        {
            this.id = id;
            this.nome = nome;
            this.username = username;
            this.cognome = cognome;
            this.mail = mail;
            this.password = password;
            this.userRole = userRole;
        }
    }
}