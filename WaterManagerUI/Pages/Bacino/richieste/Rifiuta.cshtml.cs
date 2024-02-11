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

public class Rifiuta : PageModel
{
    private GestoreIdrico user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public Rifiuta(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task<IActionResult> OnPostAsync(int richiestaId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREIDRICO"))
        {
            var client = _httpClientFactory.CreateClient();
            int id = int.Parse(User.FindFirstValue(ClaimTypes.Gender));

            try
            {
                Approvazione approvazione = new Approvazione
                {
                    approvato = false,
                    idRichiesta = richiestaId,
                    idGestore = user.id,
                    date = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss")
                };

                StringContent stringContent =
                    new StringContent(JsonConvert.SerializeObject(approvazione), Encoding.UTF8, "application/json");

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.PostAsync(
                    "http://localhost:8080/api/v1/bacino/approvazione/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    return RedirectToPage("/Bacino/VisualizzaRichiesteBacino");
                }
            }
            catch (Exception e)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }

        return RedirectToPage("/Bacino/VisualizzaRichiesteBacino");
    }
}