using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.cambio;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.disponibilita;

public class ModificaDisponibilita : PageModel
{
    [BindProperty] public Double dimNew { get; set; }
    public GestoreIdrico user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    private RisorsaIdrica risorsa { get; set; }


    public ModificaDisponibilita(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
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

    public async Task<IActionResult> OnPostAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREIDRICO"))
        {
            var client = _httpClientFactory.CreateClient();
            int id = int.Parse(User.FindFirstValue(ClaimTypes.Gender));

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var responseUser = await client.GetAsync(
                    $"http://localhost:8080/api/v1/user/get/gi/{id}");

                if (responseUser.IsSuccessStatusCode)
                {
                    var jsonResponse = await responseUser.Content.ReadAsStringAsync();
                    this.user = JsonConvert.DeserializeObject<GestoreIdrico>(jsonResponse);
                }

                risorsa = new RisorsaIdrica()
                {
                    disponibilita = dimNew,
                    consumo = user.bacinoIdrico.storicoRisorse().FirstOrDefault().consumo,
                    data = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"),
                    idSource = user.bacinoIdrico.id
                };

                String stringaDaInviare = JsonConvert.SerializeObject(risorsa);

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.PostAsync(
                    "http://localhost:8080/api/v1/risorsa/bacino/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
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


        return RedirectToPage("/Bacino/GestoreIdrico");
    }
}