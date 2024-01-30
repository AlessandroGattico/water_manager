using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaAttuatore : PageModel
{
    [BindProperty] public Attuatore attuatore { get; set; }
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;

    public CreaAttuatore(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }


    public async Task<IActionResult> OnPostAsync(int campoId)
    {
        var client = _httpClientFactory.CreateClient();

        user = new GestoreAzienda(Convert.ToInt32(User.FindFirstValue(ClaimTypes.Gender)),
            User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
            User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
            JsonConvert.DeserializeObject<Model.Item.Azienda>(User.FindFirstValue((ClaimTypes.NameIdentifier))));

        attuatore.idCampo = campoId;

        String stringaDaInviare = JsonConvert.SerializeObject(attuatore);

        try
        {
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

            StringContent stringContent =
                new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

            var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/attuatore/add",
                stringContent);

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();

                attuatore.id = JsonConvert.DeserializeObject<int>(responseContentStr);

                return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
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
}