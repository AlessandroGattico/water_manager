using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Pages;

public class VisualizzaRichiesteBacino : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public GestoreIdrico user { get; set; }

    public VisualizzaRichiesteBacino(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREIDRICO"))
        {
            var client = _httpClientFactory.CreateClient();
            int id = int.Parse(User.FindFirstValue(ClaimTypes.Gender));

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/user/get/gi/{id}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.user = JsonConvert.DeserializeObject<GestoreIdrico>(jsonResponse);
                }
            }
            catch (Exception e)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }
    }
}