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

public class CreaRichiesta : PageModel
{
    [BindProperty] public Double quantita { get; set; }
    [BindProperty] public int nomeBacino { get; set; }
    public int coltivazioneId { get; set; }
    private RichiestaIdrica richiesta { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<BacinoIdrico> bacini { get; set; }
    private GestoreAzienda gestore { get; set; }

    public CreaRichiesta(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int coltivazioneId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            this.coltivazioneId = coltivazioneId;
            this.bacini = await GetBacini();
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }
    }


    public async Task<IActionResult> OnPostAsync(int coltivazioneId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            int id = int.Parse(User.FindFirstValue(ClaimTypes.Gender));

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/user/get/ga/{id}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.gestore =
                        JsonConvert
                            .DeserializeObject<UserInterfaceWaterManager.Model.User.GestoreAzienda>(jsonResponse);
                }
            }
            catch (Exception e)
            {
                RedirectToPage("/Error/ServerOffline");
            }

            string formattedDateTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");

            this.bacini = await GetBacini();

            richiesta = new RichiestaIdrica(quantita, coltivazioneId, nomeBacino, formattedDateTime,
                gestore.azienda.nome);

            try
            {
                String stringaDaInviare = JsonConvert.SerializeObject(richiesta);

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.PostAsync("http://localhost:8080/api/v1/richiesta/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    richiesta.id = JsonConvert.DeserializeObject<int>(responseContentStr);
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

        return RedirectToPage("/Azienda/GestoreAzienda");
    }


    public async Task<HashSet<BacinoIdrico>> GetBacini()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync("http://localhost:8080/api/v1/utils/bacino/get/all");

                if (response.IsSuccessStatusCode)
                {
                    var content = await response.Content.ReadAsStringAsync();
                    return JsonConvert.DeserializeObject<HashSet<BacinoIdrico>>(content);
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

        return new HashSet<BacinoIdrico>();
    }
}