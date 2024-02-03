using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace WaterManagerUI.Pages.Azienda;

public class Aziende : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public List<Model.Item.Azienda> aziende { get; set; }

    public Aziende(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("SYSTEMADMIN"))
        {
            var client = _httpClientFactory.CreateClient();
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.GetAsync("http://localhost:8080/api/v1/admin/aziende/get/all");

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                var aziendeList = JsonConvert.DeserializeObject<HashSet<Model.Item.Azienda>>(content);

                this.aziende = aziendeList.OrderBy(a => a.nome).ToList();
            }
        }
    }
}