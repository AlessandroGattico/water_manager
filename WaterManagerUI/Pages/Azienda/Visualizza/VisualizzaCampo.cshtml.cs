using System.Net.Http.Headers;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class VisualizzaCampo : PageModel
{
    private int idCampo { get; set; }
    public Campo campo { get; set; }
    public GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;


    public VisualizzaCampo(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
    }

    public async Task OnGetAsync(int idCampo)
    {
        this.idCampo = idCampo;
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");

        var userJson = HttpContext.Session.GetString("UserSession");
        if (!string.IsNullOrEmpty(userJson))
        {
            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", jwtToken);
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/azienda/campagna/get/{this.campo}");

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
}