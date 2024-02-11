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

public class CreaBacino : PageModel
{
    [BindProperty] public String nomeBacino { get; set; }
    private BacinoIdrico bacino { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public CreaBacino(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task<IActionResult> OnPostAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREIDRICO"))
        {
            int id = int.Parse(User.FindFirstValue(ClaimTypes.Gender));
            var client = _httpClientFactory.CreateClient();
            bacino = new BacinoIdrico(nomeBacino, id);

            String stringaDaInviare = JsonConvert.SerializeObject(bacino);

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response = await client.PostAsync("http://localhost:8080/api/v1/bacino/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    bacino.id = JsonConvert.DeserializeObject<int>(responseContentStr);

                    return RedirectToPage("/Bacino/GestoreIdrico");
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

        return RedirectToPage("/Bacino/GestoreIdrico");
    }
}