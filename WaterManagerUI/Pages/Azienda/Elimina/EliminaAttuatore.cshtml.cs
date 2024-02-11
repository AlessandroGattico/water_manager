using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Elimina;

public class EliminaAttuatore : PageModel
{
    [BindProperty] public int attuatoreDel { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<Attuatore> attuatori { get; set; }


    public EliminaAttuatore(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
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
                var response =
                    await client.GetAsync($"http://localhost:8080/api/v1/azienda/attuatore/get/all/{campoId}");

                if (response.IsSuccessStatusCode)
                {
                    var content = await response.Content.ReadAsStringAsync();
                    this.attuatori = JsonConvert.DeserializeObject<HashSet<Attuatore>>(content);
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
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                await client.DeleteAsync(
                    $"http://localhost:8080/api/v1/azienda/attuatore/delete/{attuatoreDel}");
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

        return RedirectToPage("/Azienda/Visualizza/campagna/VisualizzaAttuatori",
            new { campoId = campoId });
    }
}