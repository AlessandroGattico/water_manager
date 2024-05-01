using System.ComponentModel.DataAnnotations;

namespace WaterManagerUI.Model.Security;

public class LoginRequestDTO
{
    [Required] public String username { get; set; }
    [Required] public String password { get; set; }

    public LoginRequestDTO(string username, string password)
    {
        this.username = username;
        this.password = password;
    }

    public LoginRequestDTO()
    {
    }
}