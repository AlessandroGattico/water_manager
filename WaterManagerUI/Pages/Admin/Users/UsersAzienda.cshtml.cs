using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Admin;

public class UsersAzienda : PageModel
{
    public HashSet<GestoreAzienda> gestori { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;

    public UsersAzienda(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task OnGet(int campoId)
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