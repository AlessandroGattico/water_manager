using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Security;

namespace WaterManagerUI.Pages;

public class IndexModel : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;

    public IndexModel(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
    }

    public async Task<IActionResult> OnGetAsync()
    {
        var client = _httpClientFactory.CreateClient();

        var userSession = HttpContext.Session.GetString("UserSession");
        if (!string.IsNullOrEmpty(userSession))
        {
            var user = JsonConvert.DeserializeObject<UserProfile>(userSession);

            switch (user.role)
            {
                case UserRole.GESTOREAZIENDA:
                    return RedirectToPage("/Azienda/GestoreAzienda");
                case UserRole.GESTOREIDRICO:
                    return RedirectToPage("/Bacino/GestoreIdrico");
                case UserRole.SYSTEMADMIN:
                    return RedirectToPage("/Admin");
            }
        }

        return Page();
    }
}