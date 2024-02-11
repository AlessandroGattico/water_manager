using System.ComponentModel.DataAnnotations;
using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaColtivazione : PageModel
{
    [BindProperty] public DateTime Data { get; set; }

    [BindProperty] public TimeSpan Ora { get; set; }
    [BindProperty] public string raccolto { get; set; }
    [BindProperty] public string irrigazione { get; set; }
    [BindProperty] public string esigenza { get; set; }
    [BindProperty] public Double temperatura { get; set; }
    [BindProperty] public Double umidita { get; set; }
    private Coltivazione coltivazione { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<string> raccolti { get; set; }
    public HashSet<string> irrigazioni { get; set; }
    public HashSet<string> esigenze { get; set; }
    public int campoId { get; set; }

    public CreaColtivazione(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
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
                var responseRaccolti = await client.GetAsync("http://localhost:8080/api/v1/utils/raccolto/get/all");

                if (responseRaccolti.IsSuccessStatusCode)
                {
                    var content = await responseRaccolti.Content.ReadAsStringAsync();
                    this.raccolti = JsonConvert.DeserializeObject<HashSet<string>>(content);
                }

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var responseIrrigazioni =
                    await client.GetAsync("http://localhost:8080/api/v1/utils/irrigazione/get/all");

                if (responseIrrigazioni.IsSuccessStatusCode)
                {
                    var content = await responseIrrigazioni.Content.ReadAsStringAsync();
                    this.irrigazioni = JsonConvert.DeserializeObject<HashSet<string>>(content);
                }

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var responseEsigenze = await client.GetAsync("http://localhost:8080/api/v1/utils/esigenza/get/all");

                if (responseEsigenze.IsSuccessStatusCode)
                {
                    var content = await responseEsigenze.Content.ReadAsStringAsync();
                    this.esigenze = JsonConvert.DeserializeObject<HashSet<string>>(content);
                }
            }
            catch (Exception e)
            {
                RedirectToPage("/Error/ServerOffline");
            }

            this.campoId = campoId;
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
            var client = _httpClientFactory.CreateClient();


            coltivazione = new Coltivazione
            {
                idCampo = campoId,
                semina = Data.Add(Ora).ToString("yyyy-MM-dd HH:mm:ss"),
                raccolto = raccolto,
                irrigazione = raccolto.Equals("INCOLTO") ? null : irrigazione,
                esigenza = raccolto.Equals("INCOLTO") ? null : esigenza,
                temperatura = raccolto.Equals("INCOLTO") ? null : temperatura,
                umidita = raccolto.Equals("INCOLTO") ? null : umidita
            };

            String stringaDaInviare = JsonConvert.SerializeObject(coltivazione);

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                StringContent stringContent =
                    new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

                var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/coltivazione/add",
                    stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    coltivazione.id = JsonConvert.DeserializeObject<int>(responseContentStr);
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

        return RedirectToPage("/Azienda/Visualizza/campagna/campo/coltivazione/VisualizzaColtivazioni",
            new { campoId = campoId });
    }
}