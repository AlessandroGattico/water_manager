using System.Net.Http.Headers;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;

namespace WaterManagerUI.Pages;

public class GestoreAziendaPage : PageModel
{
    private HttpClient _httpClient;
    private readonly IHttpContextAccessor _httpContextAccessor;

    public GestoreAzienda User { get; set; }

    public GestoreAziendaPage(HttpClient httpClient, IHttpContextAccessor httpContextAccessor)
    {
        _httpClient = httpClient;
        _httpContextAccessor = httpContextAccessor;
    }

    public async Task OnGetAsync(int userId)
    {
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");
        if (!string.IsNullOrEmpty(jwtToken))
        {
            try
            {
                _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
                var response = await _httpClient.GetAsync($"https://your-backend-url/api/user/{userId}");
                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    User = JsonConvert.DeserializeObject<GestoreAzienda>(jsonResponse);
                }
                else
                {
                    // Gestisci la risposta non riuscita
                }
            }
            catch (HttpRequestException e)
            {
                // Gestisci eventuali errori della richiesta HTTP
            }
        }
    }
}