using System.Net.Http.Headers;
using System.Security.Claims;
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
    private int idCampagna { get; set; }

    public CreaCampagna(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task<IActionResult> OnPostAsync(int aziendaId)
    {
        var client = _httpClientFactory.CreateClient();
        
            campagna.idAzienda = aziendaId;

            String stringaDaInviare = JsonConvert.SerializeObject(campagna);

            try
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response =
                    await client.PostAsync("http://localhost:8080/api/v1/azienda/campagna/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    idCampagna = JsonConvert.DeserializeObject<int>(responseContentStr);
                }
                else
                {
                    return RedirectToPage("/Azienda/GestoreAzienda", new { userId = user.id });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
                return RedirectToPage("/Azienda/GestoreAzienda", new { userId = user.id });
            }
        

            return RedirectToPage("/Azienda/GestoreAzienda", new { userId = user.id });
    }
}