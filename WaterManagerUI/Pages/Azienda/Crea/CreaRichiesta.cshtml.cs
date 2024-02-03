using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages;

public class CreaRichiesta : PageModel
{
    [BindProperty] public Double quantita { get; set; }
    [BindProperty] public string nomeBacino { get; set; }

    private RichiestaIdrica richiesta { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly SignInManager<IdentityUser> _signInManager;
    public HashSet<BacinoIdrico> bacini { get; set; }

    public CreaRichiesta(IHttpClientFactory httpClientFactory, SignInManager<IdentityUser> signInManager)
    {
        _httpClientFactory = httpClientFactory;
        _signInManager = signInManager;
    }

    public async Task OnGetAsync(int coltivazioneId)
    {
        this.bacini = await GetBacini();
    }


    public async Task<IActionResult> OnPostAsync(int idColtivazione)
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();
            string formattedDateTime = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");

            this.bacini = await GetBacini();
            int idBacino = TrovaIdBacinoPerNome(nomeBacino);

            richiesta = new RichiestaIdrica(quantita, idColtivazione, idBacino, formattedDateTime);

            try
            {
                String stringaDaInviare = JsonConvert.SerializeObject(richiesta);

                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                client.DefaultRequestHeaders.Authorization =
                    new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
                var response = await client.PostAsync("http://localhost:8080/api/v1/richiesta/add", stringContent);

                if (response.IsSuccessStatusCode)
                {
                    string responseContentStr = await response.Content.ReadAsStringAsync();

                    richiesta.id = JsonConvert.DeserializeObject<int>(responseContentStr);
                }
                else
                {
                    return RedirectToPage("/Azienda/GestoreAzienda",
                        new { userId = Int32.Parse(User.FindFirstValue(ClaimTypes.Gender)) });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
                return RedirectToPage("/Azienda/GestoreAzienda",
                    new { userId = Int32.Parse(User.FindFirstValue(ClaimTypes.Gender)) });
            }
        }

        return RedirectToPage("/Azienda/GestoreAzienda",
            new { userId = Int32.Parse(User.FindFirstValue(ClaimTypes.Gender)) });
    }


    public async Task<HashSet<BacinoIdrico>> GetBacini()
    {
        if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
        {
            var client = _httpClientFactory.CreateClient();
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.GetAsync("http://localhost:8080/api/v1/utils/bacino/get/all");

            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<HashSet<BacinoIdrico>>(content);
            }
        }

        return new HashSet<BacinoIdrico>();
    }

        private int TrovaIdBacinoPerNome(string nomeBacino)
        {
            if (_signInManager.IsSignedIn(User) && User.FindFirstValue(ClaimTypes.Role).Equals("GESTOREAZIENDA"))
            {
                foreach (BacinoIdrico bacino in bacini)
                {
                    if (bacino.nome.Equals(nomeBacino))
                    {
                        return bacino.id;
                    }
                }
            }

            return 0;
            }
        
    }