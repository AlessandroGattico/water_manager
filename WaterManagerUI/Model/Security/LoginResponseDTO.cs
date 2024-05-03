using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Model.Security;

public class LoginResponseDTO
{
    public UserProfile user { get; set; }
    public String jwt { get; set; }

    [JsonConstructor]
    public LoginResponseDTO(UserProfile user, string jwt)
    {
        this.user = user;
        this.jwt = jwt;
    }

    public LoginResponseDTO()
    {
    }
}