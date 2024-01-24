using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;
using WaterManagerUI.Model.Security;

namespace WaterManagerUI.Pages;

public class CreaAzienda : PageModel
{
    [BindProperty] public String nomeAzienda { get; set; }
    private Model.Item.Azienda azienda { get; set; }
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IHttpContextAccessor _httpContextAccessor;
    private readonly SignInManager<IdentityUser> _signInManager;
    private readonly UserManager<IdentityUser> _userManager;

    public CreaAzienda(IHttpClientFactory httpClientFactory,
        IHttpContextAccessor httpContextAccessor, SignInManager<IdentityUser> signInManager,
        UserManager<IdentityUser> userManager)
    {
        _httpClientFactory = httpClientFactory;
        _httpContextAccessor = httpContextAccessor;
        _signInManager = signInManager;
        _userManager = userManager;
    }

    public async Task<IActionResult> OnPostAsync(int userId)
    {
        var client = _httpClientFactory.CreateClient();
        var jwtToken = _httpContextAccessor.HttpContext.Session.GetString("JWTToken");
        var userJson = HttpContext.Session.GetString("UserSession");
        if (!string.IsNullOrEmpty(userJson))
        {
            user = JsonConvert.DeserializeObject<GestoreAzienda>(userJson);
        }

        if (!string.IsNullOrEmpty(userJson))
        {
            azienda = new Model.Item.Azienda(nomeAzienda, userId);

            String stringaDaInviare = JsonConvert.SerializeObject(azienda);

            try
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", jwtToken);
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    azienda.id = JsonConvert.DeserializeObject<int>(responseContentStr);

                    user.azienda = azienda;
                    String aziendaJson = JsonConvert.SerializeObject(azienda);

                    HttpContext.Session.SetString("UserSession", JsonConvert.SerializeObject(user));
                    var userL = await _userManager.GetUserAsync(User);
                    if (user == null)
                    {
                        return Challenge();
                    }

                    // Aggiungi il nuovo claim
                    var claim = new Claim(ClaimTypes.NameIdentifier, aziendaJson);
                    var result = await _userManager.AddClaimAsync(userL, claim);

                    if (!result.Succeeded)
                    {
                        // Gestisci l'errore
                    }

                    // Aggiorna il cookie di autenticazione
                    await _signInManager.RefreshSignInAsync(userL);


                    /*var identity = User.Identity as ClaimsIdentity;

                    if (identity != null)
                    {
                        identity.RemoveClaim(identity.FindFirst(ClaimTypes.NameIdentifier));

                        identity.AddClaim(new Claim(ClaimTypes.NameIdentifier, aziendaJson));

                        await HttpContext.SignOutAsync(IdentityConstants.ApplicationScheme);
                        await HttpContext.SignInAsync(IdentityConstants.ApplicationScheme,
                            new ClaimsPrincipal(identity));
                    }
                    */

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