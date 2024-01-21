using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaRichiesta : PageModel
{
    [BindProperty] public RichiestaIdrica richiesta { get; set; }
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    public List<BacinoIdrico> bacini { get; set; }
    private int idRichiesta { get; set; }

    public CreaRichiesta(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
    }


    public async Task<IActionResult> OnPostAsync()
    {
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");
        var userJson = HttpContext.Session.GetString("UserSession");

        try
        {
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/bacino/get/all");

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();

                bacini = JsonConvert.DeserializeObject<List<BacinoIdrico>>(responseContentStr);
                if (bacini.Count == 0)
                {
                    //ERRORE
                }
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

        if (!string.IsNullOrEmpty(userJson))
        {
            user = JsonConvert.DeserializeObject<GestoreAzienda>(userJson);

            DateTime now = DateTime.Now;
            string formattedDate = now.ToString("yyyy-MM-dd HH:mm:ss");
            richiesta.date = formattedDate;

            String stringaDaInviare = JsonConvert.SerializeObject(richiesta);

            try
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response = await client.PostAsync("http://localhost:8080/api/v1/richiesta/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    idRichiesta = JsonConvert.DeserializeObject<int>(responseContentStr);
                    richiesta.id = idRichiesta;

                    user.azienda.richieste.Add(richiesta);

                    HttpContext.Session.SetString("UserSession", JsonConvert.SerializeObject(user));
                    return RedirectToPage("/Azienda/GestoreAzienda");
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