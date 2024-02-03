using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace WaterManagerUI.Pages.Elimina;

public class EliminaEsigenza : PageModel
{
    [BindProperty] public String esigenza { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<string> esigenze { get; set; }

    public EliminaEsigenza(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor,
        SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }


    public async Task OnGetAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("SYSTEMADMIN"))
        {
            var client = _httpClientFactory.CreateClient();
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/esigenza/get/all");

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                this.esigenze = JsonConvert.DeserializeObject<HashSet<string>>(content);
            }
        }
    }

    public async Task<IActionResult> OnPostAsync()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("SYSTEMADMIN"))
        {
            var client = _httpClientFactory.CreateClient();

            if (_signInManager.IsSignedIn(User))
            {
                try
                {
                    client.DefaultRequestHeaders.Authorization =
                        new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                    await client.DeleteAsync($"http://localhost:8080/api/v1/admin/esigenza/delete/{esigenza}");
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.StackTrace);
                    return RedirectToPage("/Admin/Coltivazione/Esigenza");
                }
            }
        }

        return RedirectToPage("/Admin/Coltivazione/Esigenza");
    }
}