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

public class CreaSensore : PageModel
{
    [BindProperty] public string type { get; set; }
    [BindProperty] public string nome { get; set; }
    private GestoreAzienda user { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    private Sensore sensore { get; set; }
    public HashSet<string> types { get; set; }

    public CreaSensore(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }


    public async Task OnGetAsync(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            this.types = await GetType();
        }
    }

    public async Task<IActionResult> OnPostAsync(int campoId)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();

            user = new GestoreAzienda(Convert.ToInt32(User.FindFirstValue(ClaimTypes.Gender)),
                User.FindFirstValue(ClaimTypes.Name), User.FindFirstValue(ClaimTypes.Surname),
                User.FindFirstValue(ClaimTypes.UserData), User.FindFirstValue(ClaimTypes.Email), "",
                JsonConvert.DeserializeObject<Model.Item.Azienda>(User.FindFirstValue((ClaimTypes.NameIdentifier))));


            sensore = new Sensore()
            {
                idCampo = campoId,
                type = type,
                nome = nome
            };

            String stringaDaInviare = JsonConvert.SerializeObject(sensore);

            try
            {
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));

                StringContent stringContent =
                    new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

                var response = await client.PostAsync("http://localhost:8080/api/v1/azienda/sensore/add",
                    stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    sensore.id = JsonConvert.DeserializeObject<int>(responseContentStr);

                    return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
                }
                else
                {
                    return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
                return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
            }
        }

        return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
    }

    public async Task<HashSet<string>> GetType()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/sensorTypes/get/all");

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<HashSet<string>>(content);
            }
        }

        return new HashSet<string>();
    }
}