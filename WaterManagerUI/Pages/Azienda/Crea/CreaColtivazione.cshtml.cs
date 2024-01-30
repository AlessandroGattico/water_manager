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
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<string> raccolti { get; set; }
    public HashSet<string> irrigazioni { get; set; }
    public HashSet<string> esigenze { get; set; }
    public int campoId { get; set; }

    public CreaColtivazione(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor,
        SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }


    public async Task OnGetAsync(int campoId)
    {
        this.raccolti = await GetRaccolti();
        this.irrigazioni = await GetIrrigazioni();
        this.esigenze = await GetEsigenze();
        this.campoId = campoId;
    }

    public async Task<IActionResult> OnPostAsync(int campoId)
    {
        var client = _httpClientFactory.CreateClient();

        if (_signInManager.IsSignedIn(User))
        {
            user = new GestoreAzienda(Convert.ToInt32(User.FindFirstValue(ClaimTypes.Gender)),
                User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
                User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
                JsonConvert.DeserializeObject<Model.Item.Azienda>(User.FindFirstValue((ClaimTypes.NameIdentifier))));


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

                    return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
                }
                else
                {
                    return RedirectToPage("/Azienda/GestoreAzienda", new { userId = user.id });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
                return RedirectToPage("/Azienda/GestoreAzienda", new { userId = user.id });
            }
        }

        return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
    }

    public async Task<HashSet<string>> GetRaccolti()
    {
        var client = _httpClientFactory.CreateClient();
        client.DefaultRequestHeaders.Authorization =
            new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
        var response = await client.GetAsync("http://localhost:8080/api/v1/utils/raccolto/get/all");

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<HashSet<string>>(content);
        }

        return new HashSet<string>();
    }

    public async Task<HashSet<string>> GetIrrigazioni()
    {
        var client = _httpClientFactory.CreateClient();
        client.DefaultRequestHeaders.Authorization =
            new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
        var response = await client.GetAsync("http://localhost:8080/api/v1/utils/irrigazione/get/all");

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<HashSet<string>>(content);
        }

        return new HashSet<string>();
    }

    public async Task<HashSet<string>> GetEsigenze()
    {
        var client = _httpClientFactory.CreateClient();
        client.DefaultRequestHeaders.Authorization =
            new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
        var response = await client.GetAsync("http://localhost:8080/api/v1/utils/esigenza/get/all");

        if (response.IsSuccessStatusCode)
        {
            var content = await response.Content.ReadAsStringAsync();
            return JsonConvert.DeserializeObject<HashSet<string>>(content);
        }

        return new HashSet<string>();
    }
}