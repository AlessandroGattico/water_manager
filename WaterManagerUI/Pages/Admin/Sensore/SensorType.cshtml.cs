using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace WaterManagerUI.Pages;

public class SensorType : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<string> types { get; set; }

    public SensorType(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
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
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/sensorTypes/get/all");

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                this.types = JsonConvert.DeserializeObject<HashSet<string>>(content);
            }
        }
    }
}