using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Bacino;

public class Bacini : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public List<BacinoIdrico> bacini { get; set; }

    public Bacini(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("SYSTEMADMIN"))
        {
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync("http://localhost:8080/api/v1/admin/bacino/get/all");

                if (response.IsSuccessStatusCode)
                {
                    var content = await response.Content.ReadAsStringAsync();
                    HashSet<BacinoIdrico> baciniList = JsonConvert.DeserializeObject<HashSet<BacinoIdrico>>(content);

                    this.bacini = baciniList.OrderBy(b => b.nome).ToList();
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
}