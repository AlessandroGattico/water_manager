using System.Net.Http.Headers;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using UserInterfaceWaterManager.Model.User;
using WaterManagerUI.Model.Security;

namespace WaterManagerUI.Pages;

public class Register : PageModel
{
    [BindProperty] public RegistrationDTO registration { get; set; }
    public LoginResponseDTO? loginResponse { get; set; }
    private HttpClient client;
    private readonly IHttpContextAccessor _httpContextAccessor;

    public async Task<IActionResult> OnPostAsync()
    {
        Console.WriteLine("dentro");
        
        if (!ModelState.IsValid)
        {
            return Page();
        }

        this.client = new HttpClient();
        //_httpContextAccessor = new HttpContextAccessor();

       
        String stringaDaInviare = JsonConvert.SerializeObject(registration);

        try
        {
            StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
            var response = await client.PostAsync("http://localhost:8080/api/v1/auth/register", stringContent);

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();
                // Deserializza il JWT e altre informazioni dall'oggetto LoginResponseDTO
                loginResponse = JsonConvert.DeserializeObject<LoginResponseDTO>(responseContentStr);


                Console.WriteLine(loginResponse.jwt);
            }
            else
            {
                // Gestisci la risposta in caso di errore (es: credenziali non valide)
                // Restituisci null o un oggetto LoginResponseDTO appropriato
                return Page();
            }
        }
        catch (Exception ex)
        {
            // Gestisci eventuali eccezioni (es: problemi di connessione)
            // Potresti voler registrare questo errore o mostrare un messaggio all'utent

            Console.WriteLine(ex.StackTrace);
            return Page();
        }

        if (loginResponse != null && !string.IsNullOrEmpty(loginResponse.jwt))
        {
            // Salva il token JWT in una sessione o cookie
            HttpContext.Session.SetString("JWTToken", loginResponse.jwt);

            switch (loginResponse.user.role)
            {
                case UserRole.GESTOREAZIENDA:
                    return RedirectToPage("./Azienda/GestoreAzienda", new { userId = loginResponse.user.id });
                case UserRole.GESTOREIDRICO:
                    return RedirectToPage("./Bacino/GestoreIdrico", new { userId = loginResponse.user.id });
                case UserRole.SYSTEMADMIN:
                    return RedirectToPage("./Admin", new { userId = loginResponse.user.id });
            }
        }
        else
        {
            Console.WriteLine("vuoto");
        }

        return Page();
    }
}