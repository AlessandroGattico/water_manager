using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class GestoreIdricoPage : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    public GestoreIdrico user { get; set; }
    public BacinoIdrico bacino { get; set; }

    public GestoreIdricoPage(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task OnGetAsync()
    {
        var client = _httpClientFactory.CreateClient();

        if (ClaimTypes.NameIdentifier != "null")
        {
            user = new GestoreIdrico(Int32.Parse(User.FindFirstValue(ClaimTypes.Gender)),
                User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
                User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
                JsonConvert.DeserializeObject<BacinoIdrico>(
                    User.FindFirstValue((ClaimTypes.NameIdentifier))));
        }
        else
        {
            user = new GestoreIdrico(Int32.Parse(User.FindFirstValue(ClaimTypes.Gender)),
                User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
                User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "", null);
        }

        try
        {
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.GetAsync(
                $"http://localhost:8080/api/v1/bacino/get/gestore/{user.id}");

            if (response.IsSuccessStatusCode)
            {
                var jsonResponse = await response.Content.ReadAsStringAsync();
                this.bacino = JsonConvert.DeserializeObject<BacinoIdrico>(jsonResponse);
            }
        }
        catch (Exception e)
        {
        }
    }
}