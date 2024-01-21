using System.Net.Http.Headers;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class VisualizzaCampagna : PageModel
{
    public int campagnaId { get; set; }
    public Campagna campagna { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;


    public VisualizzaCampagna(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
    }

    public async Task OnGetAsync(int campagnaId)
    {
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");

        this.campagnaId = campagnaId;

        var userJson = HttpContext.Session.GetString("UserSession");

        if (!string.IsNullOrEmpty(userJson))
        {
            try
            {
                //this.campagnaId = campagnaId;

                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", jwtToken);
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/campagna/get/{this.campagnaId}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.campagna = JsonConvert.DeserializeObject<Campagna>(jsonResponse);
                }
            }
            catch (HttpRequestException e)
            {
            }
        }
    }
}