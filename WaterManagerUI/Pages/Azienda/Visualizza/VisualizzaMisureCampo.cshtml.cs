using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class VisualizzaMisureCampo : PageModel
{
    public Campo campo { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;

    public VisualizzaMisureCampo(IHttpClientFactory httpClientFactory)
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
                $"http://localhost:8080/api/v1/azienda/campo/get/{campoId}");

            if (response.IsSuccessStatusCode)
            {
                var jsonResponse = await response.Content.ReadAsStringAsync();
                this.campo = JsonConvert.DeserializeObject<Campo>(jsonResponse);
            }
        }
        catch (Exception e)
        {
        }
    }
}