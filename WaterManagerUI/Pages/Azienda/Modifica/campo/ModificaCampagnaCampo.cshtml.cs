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

public class ModificaCampagnaCampo : PageModel
{
    [BindProperty] public int campagnaNew { get; set; }
    public Campo campo { get; set; }
    public HashSet<Campagna> campagne { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    private CambioInt cambioInt { get; set; }

    public ModificaCampagnaCampo(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int aziendaId, int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            this.campagne = await this.GetCampagne(aziendaId);
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }
    }

    public async Task<IActionResult> OnPostAsync(int aziendaId, int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();
            string formattedDateTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");

            this.campagne = await GetCampagne(aziendaId);

            cambioInt = new CambioInt(campoId, campagnaNew, "id_campagna");

            try
            {
                String stringaDaInviare = JsonConvert.SerializeObject(cambioInt);

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/campo/modifica/campagna",
                    stringContent);

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
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

        return RedirectToPage("/Azienda/GestoreAzienda",
            new { userId = Int32.Parse(User.FindFirstValue(ClaimTypes.Gender)) });
    }

    public async Task<HashSet<Campagna>> GetCampagne(int aziendaId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.GetAsync($"http://localhost:8080/api/v1/azienda/campagna/get/all/{aziendaId}");

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<HashSet<Campagna>>(content);
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }

        return new HashSet<Campagna>();
    }
}