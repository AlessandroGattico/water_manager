using System.Net.Http.Headers;
using System.Security.Claims;
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
    private int idCampagna { get; set; }

    public CreaCampo(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task<IActionResult> OnPostAsync(int campagnaId)
    {
        this.idCampagna = campagnaId;
        var client = _httpClientFactory.CreateClient();

        user = new GestoreAzienda(Convert.ToInt32(User.FindFirstValue(ClaimTypes.Gender)),
            User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
            User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
            JsonConvert.DeserializeObject<Model.Item.Azienda>(User.FindFirstValue((ClaimTypes.NameIdentifier))));

        campo.idCampagna = this.idCampagna;

        String stringaDaInviare = JsonConvert.SerializeObject(campo);

        try
        {
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
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
                    new { campagnaId = idCampagna });
            }
        }
        catch (Exception ex)
        {
            return RedirectToPage("/Azienda/Visualizza/VisualizzaCampagna", new { campagnaId = this.idCampagna });
        }
    }
}