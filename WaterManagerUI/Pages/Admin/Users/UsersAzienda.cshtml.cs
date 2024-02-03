using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Admin;

public class UsersAzienda : PageModel
{
    public HashSet<GestoreAzienda> gestori { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;


    public UsersAzienda(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGet(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("SYSTEMADMIN"))
        {
            var client = _httpClientFactory.CreateClient();

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/admin/ga/get/all");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.gestori = JsonConvert.DeserializeObject<HashSet<GestoreAzienda>>(jsonResponse);
                }
            }
            catch (Exception e)
            {
            }
        }
    }
}