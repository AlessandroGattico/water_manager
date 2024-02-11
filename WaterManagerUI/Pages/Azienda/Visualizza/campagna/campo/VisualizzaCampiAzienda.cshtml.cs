using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Pages;

public class VisualizzaCampiAzienda : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public Model.Item.Azienda azienda { get; set; }


    public VisualizzaCampiAzienda(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int aziendaId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();
            
            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/get/{aziendaId}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.azienda = JsonConvert.DeserializeObject<Model.Item.Azienda>(jsonResponse);
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