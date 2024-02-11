using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;
using WaterManagerUI.Model.Security;

namespace WaterManagerUI.Pages;

public class CreaAzienda : PageModel
{
    [BindProperty] public String nomeAzienda { get; set; }
    private Model.Item.Azienda azienda { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public CreaAzienda(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task<IActionResult> OnPostAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            int id = int.Parse(User.FindFirstValue(ClaimTypes.Gender));
            var client = _httpClientFactory.CreateClient();
            azienda = new Model.Item.Azienda(nomeAzienda, id);

            String stringaDaInviare = JsonConvert.SerializeObject(azienda);

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    azienda.id = JsonConvert.DeserializeObject<int>(responseContentStr);

                    return RedirectToPage("/Azienda/GestoreAzienda");
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
}