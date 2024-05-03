using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

/**
 * @author Gattico Alessandro
 */
public class VisualizzaAttuatore : PageModel
{
    public Attuatore attuatore { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    private Attivazione attivazione { get; set; }


    public VisualizzaAttuatore(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int attuatoreId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/attuatore/get/{attuatoreId}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.attuatore = JsonConvert.DeserializeObject<Attuatore>(jsonResponse);
                }
            }
            catch (HttpRequestException e)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }
    }

    public async Task<IActionResult> OnPostAsync(int attuatoreId, String app)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            attivazione = new Attivazione
            {
                current = app == "ON",
                time = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"),
                idAttuatore = attuatoreId
            };


            String stringaDaInviare = JsonConvert.SerializeObject(attivazione);

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                var responseA = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/attuatore/get/{attuatoreId}");

                if (responseA.IsSuccessStatusCode)
                {
                    var jsonResponse = await responseA.Content.ReadAsStringAsync();
                    this.attuatore = JsonConvert.DeserializeObject<Attuatore>(jsonResponse);

                    client.DefaultRequestHeaders.Authorization =
                        new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                    StringContent stringContent =
                        new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

                    var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/attivazione/add",
                        stringContent);

                    if (response.IsSuccessStatusCode)
                    {
                        return RedirectToPage("/Azienda/Visualizza/campagna/campo/attuatori/VisualizzaAttuatori",
                            new { campoId = attuatore.idCampo });
                    }
                }
            }
            catch (Exception ex)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }

        return RedirectToPage("/Azienda/Visualizza/campagna/campo/attuatori/VisualizzaAttuatori",
            new { campoId = attuatore.idCampo });
    }
}