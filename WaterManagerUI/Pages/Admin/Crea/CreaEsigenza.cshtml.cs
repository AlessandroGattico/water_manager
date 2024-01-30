using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace WaterManagerUI.Pages.Crea;

public class CreaEsigenza : PageModel
{
    [BindProperty] public String esigenza { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;

    public CreaEsigenza(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task<IActionResult> OnPostAsync(int userId)
    {
        var client = _httpClientFactory.CreateClient();

        try
        {
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

            await client.GetAsync($"http://localhost:8080/api/v1/admin/esigenza/add/{esigenza.ToUpper()}");
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.StackTrace);
            return RedirectToPage("/Admin/Coltivazione/Esigenza");
        }

        return RedirectToPage("/Admin/Coltivazione/Esigenza");
    }
}