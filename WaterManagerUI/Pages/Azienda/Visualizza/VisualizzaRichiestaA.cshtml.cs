using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Bacino.richieste;

public class VisualizzaRichiestaA : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public GestoreAzienda user { get; set; }
    public RichiestaIdrica richiestaIdrica { get; set; }

    public VisualizzaRichiestaA(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int richiestaId)
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
                    this.user = JsonConvert.DeserializeObject<GestoreAzienda>(jsonResponse);

                    var responseRichiesta = await client.GetAsync(
                        $"http://localhost:8080/api/v1/richiesta/get/{richiestaId}");

                    if (responseRichiesta.IsSuccessStatusCode)
                    {
                        var jsonRichiesta = await responseRichiesta.Content.ReadAsStringAsync();
                        this.richiestaIdrica = JsonConvert.DeserializeObject<RichiestaIdrica>(jsonRichiesta);
                    }
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