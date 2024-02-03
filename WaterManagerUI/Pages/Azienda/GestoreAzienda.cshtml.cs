using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class GestoreAziendaPage : PageModel
{
    public GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public Model.Item.Azienda azienda { get; set; }


    public GestoreAziendaPage(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            int id = Int32.Parse(HttpContext.Session.GetString("UserId"));
            
            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/user/get/ga/{id}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.user = JsonConvert.DeserializeObject<GestoreAzienda>(jsonResponse);

                    var responseaz = await client.GetAsync(
                        $"http://localhost:8080/api/v1/azienda/get/gestore/{user.id}");

                    if (responseaz.IsSuccessStatusCode)
                    {
                        var jsonResponseaz = await response.Content.ReadAsStringAsync();
                        this.azienda = JsonConvert.DeserializeObject<Model.Item.Azienda>(jsonResponse);
                    }
                }
            }
            catch (Exception e)
            {
            }
        }
    }
}