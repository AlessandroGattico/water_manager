using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class VisualizzaCampagne : PageModel
{
    public Model.Item.Azienda azienda { get; set; }
    public List<Campagna> campagne { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public VisualizzaCampagne(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int aziendaId)
    {
        if (_signInManager.IsSignedIn(User))
        {
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/get/{aziendaId}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    azienda = JsonConvert.DeserializeObject<Model.Item.Azienda>(jsonResponse);

                    if (azienda.campagne.Any())
                    {
                        this.campagne = azienda.campagne.OrderBy(c => c.nome).ToList();
                    }
                    else
                    {
                        this.campagne = new List<Campagna>();
                    }
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