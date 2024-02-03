using System.Net.Http.Headers;
using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace WaterManagerUI.Pages.Elimina;

public class EliminaRaccolto : PageModel
{
    [BindProperty] public String raccolto { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<string> raccolti { get; set; }

    public EliminaRaccolto(IHttpClientFactory httpClientFactory, IHttpContextAccessor httpContextAccessor,
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
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/raccolto/get/all");

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                this.raccolti = JsonConvert.DeserializeObject<HashSet<string>>(content);
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

                    await client.DeleteAsync($"http://localhost:8080/api/v1/admin/raccolto/delete/{raccolto}");
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.StackTrace);
                    return RedirectToPage("/Admin/Coltivazione/Raccolto");
                }
            }
        }

        return RedirectToPage("/Admin/Coltivazione/Raccolto");
    }
}