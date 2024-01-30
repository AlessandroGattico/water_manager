using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace WaterManagerUI.Pages.Crea;

public class CreaRaccolto : PageModel
{
    [BindProperty] public String raccolto { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;

    public CreaRaccolto(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task<IActionResult> OnPostAsync()
    {
        var client = _httpClientFactory.CreateClient();

        try
        {
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            StringContent stringContent = new StringContent(raccolto.ToUpper(), Encoding.UTF8, "application/json");

            var response =
                await client.PostAsync("http://localhost:8080/api/v1/admin/raccolto/add", stringContent);
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.StackTrace);
            return RedirectToPage("/Admin/Coltivazione/Raccolto");
        }

        return RedirectToPage("/Admin/Coltivazione/Raccolto");
    }
}