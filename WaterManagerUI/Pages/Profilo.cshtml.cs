using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Pages;

public class Profilo : PageModel
{
    public UserProfile user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public Profilo(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync()
    {
        if (_signInManager.IsSignedIn(User))
        {
            switch (User.FindFirstValue(ClaimTypes.Role))
            {
                case "SYSTEMADMIN":
                    user = new UserProfile(User.FindFirstValue(ClaimTypes.Name),
                        User.FindFirstValue(ClaimTypes.Surname),
                        User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
                        UserRole.SYSTEMADMIN);
                    break;
                case "GESTOREAZIENDA":
                    user = new UserProfile(User.FindFirstValue(ClaimTypes.Name),
                        User.FindFirstValue(ClaimTypes.Surname),
                        User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
                        UserRole.GESTOREAZIENDA);
                    break;
                case "GESTOREIDRICO":
                    user = new UserProfile(User.FindFirstValue(ClaimTypes.Name),
                        User.FindFirstValue(ClaimTypes.Surname),
                        User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
                        UserRole.GESTOREIDRICO);
                    break;
            }
        }
    }
}