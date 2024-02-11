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

public class ModificaDimensioneCampo : PageModel
{
    [BindProperty] public Double dimNew { get; set; }
    public Campo campo { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    private CambioDouble cambioDim { get; set; }


    public ModificaDimensioneCampo(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
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
        } else
        {
            RedirectToPage("/Error/UserNotLogged");
        }
    }

    public async Task<IActionResult> OnPostAsync(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            cambioDim = new CambioDouble(campoId, dimNew, "dimensione");

            var client = _httpClientFactory.CreateClient();

            try
            {
                String stringaDaInviare = JsonConvert.SerializeObject(cambioDim);

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.PostAsync(
                    "http://localhost:8080/api/v1/azienda/campo/modifica/nome", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    bool esito = JsonConvert.DeserializeObject<bool>(jsonResponse);
                    
                }
            }
            catch (Exception e)
            {
                RedirectToPage("/Error/ServerOffline");
            }

            return RedirectToPage("/Azienda/Visualizza/campagna/campo/VisualizzaCampo", new { campoId = campoId });
        } else
        {
            RedirectToPage("/Error/UserNotLogged");
        }

        return RedirectToPage("/Azienda/Visualizza/campagna/campo/VisualizzaCampo", new { campoId = campoId });
    }
}