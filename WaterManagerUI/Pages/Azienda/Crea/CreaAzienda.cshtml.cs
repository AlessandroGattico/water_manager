using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;
using WaterManagerUI.Model.Security;

namespace WaterManagerUI.Pages;

public class CreaAzienda : PageModel
{
    [BindProperty] public Model.Item.Azienda azienda { get; set; }
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    private int idAzienda { get; set; }

    public CreaAzienda(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
    }

    public async Task<IActionResult> OnPostAsync()
    {
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");
        var userJson = HttpContext.Session.GetString("UserSession");


        if (!string.IsNullOrEmpty(userJson))
        {
            user = JsonConvert.DeserializeObject<GestoreAzienda>(userJson);


            azienda.idGestore = user.id;

            String stringaDaInviare = JsonConvert.SerializeObject(azienda);

            try
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    idAzienda = JsonConvert.DeserializeObject<int>(responseContentStr);
                    user.azienda.id = idAzienda;
                    
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