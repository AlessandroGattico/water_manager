using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class VisualizzaColtivazioni : PageModel
{
    public Campo campo { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    private readonly SignInManager<IdentityUser> _signInManager;


    public VisualizzaColtivazioni(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
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

                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/campo/get/{campoId}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.campo = JsonConvert.DeserializeObject<Campo>(jsonResponse);
                }
            }
            catch (HttpRequestException e)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }
    }
}