using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaColtivazione : PageModel
{
    [BindProperty] public Coltivazione coltivazione { get; set; }
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    public List<String> raccolti { get; set; }
    public List<String> irrigazioni { get; set; }
    public List<String> esigenze { get; set; }
    private int idColtivazione { get; set; }

    public CreaColtivazione(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor)
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
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/raccolto/get/all");

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();

                raccolti = JsonConvert.DeserializeObject<List<String>>(responseContentStr);
                if (raccolti.Count == 0)
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

        try
        {
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/irrigazione/get/all");

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();

                irrigazioni = JsonConvert.DeserializeObject<List<String>>(responseContentStr);
                if (irrigazioni.Count == 0)
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

        try
        {
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/esigenza/get/all");

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();

                esigenze = JsonConvert.DeserializeObject<List<String>>(responseContentStr);
                if (esigenze.Count == 0)
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
            coltivazione.semina = formattedDate;

            String stringaDaInviare = JsonConvert.SerializeObject(coltivazione);

            try
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/coltivazione/add34" +
                                                      "",
                    stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    idColtivazione = JsonConvert.DeserializeObject<int>(responseContentStr);
                    coltivazione.id = idColtivazione;


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