using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Elimina;

public class EliminaSensore : PageModel
{
    [BindProperty] public String nome { get; set; }
    public HashSet<Sensore> sensori { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public EliminaSensore(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async void OnGet(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESORREAZIENDA"))
        {
            this.GetSensori(campoId);
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

            int idSensore = this.TrovaIdSensorePerNome(nome);

            if (idSensore > 0)
            {
                try
                {
                    client.DefaultRequestHeaders.Authorization =
                        new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                    await client.DeleteAsync($"http://localhost:8080/api/v1/azienda/sensore/delete/{idSensore}");
                }
                catch (Exception ex)
                {
                    RedirectToPage("/Error/ServerOffline");
                }
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }

        return RedirectToPage("/Admin/Coltivazione/Irrigazione");
    }

    private async void GetSensori(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                var response = await client.GetAsync("http://localhost:8080/api/v1/azienda/add");

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    sensori = JsonConvert.DeserializeObject<HashSet<Sensore>>(responseContentStr);
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
    }

    private int TrovaIdSensorePerNome(string nomeBacino)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            foreach (Sensore sensore in sensori)
            {
                if (sensore.nome.Equals(nomeBacino))
                {
                    return sensore.id;
                }
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }

        return 0;
    }
}