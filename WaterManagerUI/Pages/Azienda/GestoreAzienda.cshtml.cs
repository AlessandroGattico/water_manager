using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class GestoreAziendaPage : PageModel
{
    public GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    private readonly SignInManager<IdentityUser> _signInManager;
    public Model.Item.Azienda azienda { get; set; }
    private int idAzienda { get; set; }


    public GestoreAziendaPage(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync()
    {
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");

        var userJson = HttpContext.Session.GetString("UserSession");
        if (!string.IsNullOrEmpty(userJson))
        {
            user = JsonConvert.DeserializeObject<GestoreAzienda>(userJson);
        }

        if (_signInManager.IsSignedIn(User))
        {
            idAzienda = Convert.ToInt32(User.FindFirstValue(ClaimTypes.NameIdentifier));

            try
            {
                //this.campagnaId = campagnaId;

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", jwtToken);
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/get/{idAzienda}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.azienda = JsonConvert.DeserializeObject<Model.Item.Azienda>(jsonResponse);
                }
            }
            catch (HttpRequestException e)
            {
            }
        }
        else
        {
            this.azienda = user.azienda;
        }
    }
}