using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaBacino : PageModel
{
    [BindProperty] public String nomeBacino { get; set; }
    private BacinoIdrico bacino { get; set; }
    private GestoreIdrico user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;

    public CreaBacino(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task<IActionResult> OnPostAsync(int userId)
    {
        var client = _httpClientFactory.CreateClient();
        bacino = new BacinoIdrico(nomeBacino, userId);

        String stringaDaInviare = JsonConvert.SerializeObject(bacino);

        try
        {
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

            StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
            var response = await client.PostAsync("http://localhost:8080/api/v1/bacino/add", stringContent);

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();

                bacino.id = JsonConvert.DeserializeObject<int>(responseContentStr);

                return RedirectToPage("/Bacino/GestoreIdrico");
            }
            else
            {
                return RedirectToPage("/Bacino/GestoreIdrico");
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.StackTrace);
            return RedirectToPage("/Bacino/GestoreIdrico");
        }
    }
}