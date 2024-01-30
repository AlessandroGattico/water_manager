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
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    private readonly SignInManager<IdentityUser> _signInManager;
    private readonly UserManager<IdentityUser> _userManager;

    public CreaAzienda(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor, SignInManager<IdentityUser> signInManager,
        UserManager<IdentityUser> userManager)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
        _signInManager = signInManager;
        _userManager = userManager;
    }

    public async Task<IActionResult> OnPostAsync(int userId)
    {
        var client = _httpClientFactory.CreateClient();
        azienda = new Model.Item.Azienda(nomeAzienda, userId);

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

                return RedirectToPage("/Azienda/GestoreAzienda", new { userId = user.id });
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
}