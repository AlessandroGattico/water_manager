using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Bacino.richieste;

/**
 * @author Gattico Alessandro
 */
public class VisualizzaRichiestaB : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;

    public GestoreIdrico user { get; set; }
    public RichiestaIdrica richiestaIdrica { get; set; }
    public Approvazione approvazione { get; set; }

    public VisualizzaRichiestaB(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int richiestaId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREIDRICO"))
        {
            var client = _httpClientFactory.CreateClient();
            int id = int.Parse(User.FindFirstValue(ClaimTypes.Gender));

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.GetAsync(
                    $"http://localhost:8080/api/v1/user/get/gi/{id}");

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();
                    this.user = JsonConvert.DeserializeObject<GestoreIdrico>(jsonResponse);

                    var responseRichiesta = await client.GetAsync(
                        $"http://localhost:8080/api/v1/richiesta/get/{richiestaId}");

                    if (responseRichiesta.IsSuccessStatusCode)
                    {
                        var jsonRichiesta = await responseRichiesta.Content.ReadAsStringAsync();
                        try
                        {
                            this.richiestaIdrica = JsonConvert.DeserializeObject<RichiestaIdrica>(jsonRichiesta);
                        }
                        catch (Exception e)
                        {
                            var error = JsonConvert.DeserializeObject<String>(jsonRichiesta);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }
    }


    public async Task<IActionResult> OnPostAsync(int richiestaId, String app)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREIDRICO"))
        {
            var client = _httpClientFactory.CreateClient();

            approvazione = new Approvazione
            {
                approvato = app == "Approva",
                date = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss"),
                idGestore = int.Parse(User.FindFirstValue(ClaimTypes.Gender)),
                idRichiesta = richiestaId
            };


            String stringaDaInviare = JsonConvert.SerializeObject(approvazione);

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                StringContent stringContent =
                    new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

                var response = await client.PostAsync("http://localhost:8080/api/v1/bacino/approvazione/add",
                    stringContent);

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToPage("/Bacino/richieste/VisualizzaRichiesteBacino");
                }
            }
            catch (Exception ex)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }
        else
        {
            RedirectToPage("/Error/UserNotLogged");
        }

        return RedirectToPage("/Bacino/richieste/VisualizzaRichiesteBacino");
    }
}