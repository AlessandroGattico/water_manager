using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Pages;

public class Profilo : PageModel
{
    public GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;


    public Profilo(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
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
        
    }
}