using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaCampagna : PageModel
{
    [BindProperty] public Campagna campagna { get; set; }

    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    private int idCampagna { get; set; }

    public CreaCampagna(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
    }

    public async Task<IActionResult> OnPostAsync(int aziendaId)
    {
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");
        var userJson = HttpContext.Session.GetString("UserSession");


        if (!string.IsNullOrEmpty(userJson))
        {
            user = JsonConvert.DeserializeObject<GestoreAzienda>(userJson);


            campagna.idAzienda = aziendaId;

            String stringaDaInviare = JsonConvert.SerializeObject(campagna);

            try
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response =
                    await client.PostAsync("http://localhost:8080/api/v1/azienda/campagna/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    idCampagna = JsonConvert.DeserializeObject<int>(responseContentStr);
                    campagna.id = idCampagna;
                    user.azienda.campagne.Add(campagna);

                    HttpContext.Session.SetString("UserSession", JsonConvert.SerializeObject(user));
                }
                else
                {
                    return RedirectToPage("/Azienda/GestoreAzienda");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
                return RedirectToPage("/Azienda/GestoreAzienda");
            }
        }

        return RedirectToPage("/Azienda/GestoreAzienda");
    }
}