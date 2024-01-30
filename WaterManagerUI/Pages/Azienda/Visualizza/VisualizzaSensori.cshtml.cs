using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class VisualizzaSensori : PageModel
{
    public Campo campo { get; set; }
    public GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;


    public VisualizzaSensori(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task OnGetAsync(int campoId)
    {
        var client = _httpClientFactory.CreateClient();

        user = new GestoreAzienda(Convert.ToInt32(User.FindFirstValue(ClaimTypes.Gender)),
            User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
            User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
            JsonConvert.DeserializeObject<Model.Item.Azienda>(User.FindFirstValue((ClaimTypes.NameIdentifier))));

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