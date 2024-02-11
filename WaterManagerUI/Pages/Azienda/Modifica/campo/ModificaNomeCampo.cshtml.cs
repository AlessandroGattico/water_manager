using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.cambio;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Modifica.campo;

public class ModificaNomeCampo : PageModel
{
    [BindProperty] public String nomeNew { get; set; }
    public Campo campo { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    private CambioString cambioNome { get; set; }


    public ModificaNomeCampo(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/campo/get/{campoId}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.campo = JsonConvert.DeserializeObject<Campo>(jsonResponse);
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

    public async Task<IActionResult> OnPostAsync(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            cambioNome = new CambioString(campoId, nomeNew, "nome");

            var client = _httpClientFactory.CreateClient();

            try
            {
                String stringaDaInviare = JsonConvert.SerializeObject(cambioNome);

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.PostAsync(
                    "http://localhost:8080/api/v1/azienda/campo/modifica/nome", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    bool esito = JsonConvert.DeserializeObject<bool>(jsonResponse);

                    if (esito)
                    {
                        return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
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

        return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
    }
}