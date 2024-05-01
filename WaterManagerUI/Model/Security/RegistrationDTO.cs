using System.ComponentModel.DataAnnotations;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Model.Security;

public class RegistrationDTO
{
    [Required] public String nome { get; set; }
    [Required] public String cognome { get; set; }
    [Required] public String username { get; set; }
    [Required] public String mail { get; set; }
    [Required] public String password { get; set; }
    [Required] public UserRole role { get; set; }

    public RegistrationDTO()
    {
    }

    public RegistrationDTO(string nome, string cognome, string username, string mail, string password, UserRole role)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }
}