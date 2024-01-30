using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class AdminPage : PageModel
{
    public UserInterfaceWaterManager.Model.User.Admin user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public ElementsCount count { get; set; }


    public AdminPage(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor,
        SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync()
    {
        var client = _httpClientFactory.CreateClient();

        user = new UserInterfaceWaterManager.Model.User.Admin(Int32.Parse(User.FindFirstValue(ClaimTypes.Gender)),
            User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
            User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "");


        if (_signInManager.IsSignedIn(User))
        {
            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/admin/count");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.count = JsonConvert.DeserializeObject<ElementsCount>(jsonResponse);
                }
            }
            catch (HttpRequestException e)
            {
            }
        }
    }
}