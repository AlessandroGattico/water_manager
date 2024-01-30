using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Pages.Admin;

public class UsersBacino : PageModel
{
    public HashSet<GestoreIdrico> gestori { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;

    public UsersBacino(IHttpClientFactory httpClientFactory)
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
                $"http://localhost:8080/api/v1/admin/gi/get/all");

            if (response.IsSuccessStatusCode)
            {
                var jsonResponse = await response.Content.ReadAsStringAsync();
                this.gestori = JsonConvert.DeserializeObject<HashSet<GestoreIdrico>>(jsonResponse);
            }
        }
        catch (Exception e)
        {
        }
    }
}