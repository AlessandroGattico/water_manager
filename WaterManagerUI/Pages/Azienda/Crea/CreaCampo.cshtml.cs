using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaCampo : PageModel
{
    [BindProperty] public Campo campo { get; set; }
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    private int idCampagna { get; set; }

    public CreaCampo(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
    }

    public async Task<IActionResult> OnPostAsync(int campagnaId)
    {
        this.idCampagna = campagnaId;
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");
        var userJson = HttpContext.Session.GetString("UserSession");


        if (!string.IsNullOrEmpty(userJson))
        {
            user = JsonConvert.DeserializeObject<GestoreAzienda>(userJson);


            campo.idCampagna = this.idCampagna;

            String stringaDaInviare = JsonConvert.SerializeObject(campo);

            try
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response =
                    await client.PostAsync("http://localhost:8080/api/v1/azienda/campo/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    var idcampo = JsonConvert.DeserializeObject<int>(responseContentStr);
                    return RedirectToPage("/Azienda/Visualizza/VisualizzaCampagna",
                        new { campagnaId = this.idCampagna });
                }
                else
                {
                    return RedirectToPage("/Azienda/Visualizza/VisualizzaCampagna",
                        new { campagnaId = this.idCampagna });
                }
            }
            catch (Exception ex)
            {
                return RedirectToPage("/Azienda/Visualizza/VisualizzaCampagna", new { campagnaId = this.idCampagna });
            }
        }

        return RedirectToPage("/Azienda/Visualizza/VisualizzaCampagna", new { campagnaId = this.idCampagna });
    }
}