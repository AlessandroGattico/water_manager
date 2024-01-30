using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
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
                    GestoreAzienda userA;
                    try
                    {
                        client.DefaultRequestHeaders.Authorization =
                            new AuthenticationHeaderValue("Bearer", loginResponse.jwt);
                        var response = await client.GetAsync(
                            $"http://localhost:8080/api/v1/user/get/ga/{loginResponse.user.id}");

                        if (response.IsSuccessStatusCode)
                        {
                            var jsonResponse = await response.Content.ReadAsStringAsync();
                            userA = JsonConvert.DeserializeObject<GestoreAzienda>(jsonResponse);


                            if (userA != null)
                            {
                                List<Claim> claims;

                                if (userA.azienda != null)
                                {
                                    String aziendaJson = JsonConvert.SerializeObject(userA.azienda);


                                    claims = new List<Claim>
                                    {
                                        new Claim(ClaimTypes.Gender, userA.id.ToString()),
                                        new Claim(ClaimTypes.Surname, userA.cognome),
                                        new Claim(ClaimTypes.Name, userA.nome),
                                        new Claim(ClaimTypes.Email, userA.mail),
                                        new Claim(ClaimTypes.UserData, userA.username),
                                        new Claim(ClaimTypes.Role, userA.role.ToString()),
                                        new Claim(ClaimTypes.Authentication, loginResponse.jwt),
                                        new Claim(ClaimTypes.NameIdentifier, aziendaJson)
                                    };
                                }
                                else
                                {
                                    claims = new List<Claim>
                                    {
                                        new Claim(ClaimTypes.Gender, userA.id.ToString()),
                                        new Claim(ClaimTypes.Surname, userA.cognome),
                                        new Claim(ClaimTypes.Name, userA.nome),
                                        new Claim(ClaimTypes.Email, userA.mail),
                                        new Claim(ClaimTypes.UserData, userA.username),
                                        new Claim(ClaimTypes.Role, userA.role.ToString()),
                                        new Claim(ClaimTypes.Authentication, loginResponse.jwt)
                                    };
                                }

                                var claimsIdentity = new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

                                var authProperties = new AuthenticationProperties
                                {
                                    IsPersistent =
                                        false, // o false, a seconda se vuoi che la sessione persista tra le sessioni del browser
                                    ExpiresUtc =
                                        DateTimeOffset.UtcNow
                                            .AddMinutes(30) // imposta un tempo di scadenza del cookie, se necessario
                                };

                                await HttpContext.SignInAsync(IdentityConstants.ApplicationScheme,
                                    new ClaimsPrincipal(claimsIdentity),
                                    authProperties);
                            }
                        }
                        else
                        {
                            return Page();
                        }
                    }
                    catch (HttpRequestException e)
                    {
                        return Page();
                    }

                    return RedirectToPage("./Azienda/GestoreAzienda");
                case UserRole.GESTOREIDRICO:
                    GestoreIdrico userI;
                    try
                    {
                        client.DefaultRequestHeaders.Authorization =
                            new AuthenticationHeaderValue("Bearer", loginResponse.jwt);
                        var response = await client.GetAsync(
                            $"http://localhost:8080/api/v1/user/get/gi/{loginResponse.user.id}");

                        if (response.IsSuccessStatusCode)
                        {
                            var jsonResponse = await response.Content.ReadAsStringAsync();
                            userI = JsonConvert.DeserializeObject<GestoreIdrico>(jsonResponse);


                            if (userI != null)
                            {
                                List<Claim> claims;

                                if (userI.bacinoIdrico != null)
                                {
                                    String bacinoJson = JsonConvert.SerializeObject(userI.bacinoIdrico);


                                    claims = new List<Claim>
                                    {
                                        new Claim(ClaimTypes.Gender, userI.id.ToString()),
                                        new Claim(ClaimTypes.Surname, userI.cognome),
                                        new Claim(ClaimTypes.Name, userI.nome),
                                        new Claim(ClaimTypes.Email, userI.mail),
                                        new Claim(ClaimTypes.UserData, userI.username),
                                        new Claim(ClaimTypes.Role, userI.role.ToString()),
                                        new Claim(ClaimTypes.Authentication, loginResponse.jwt),
                                        new Claim(ClaimTypes.NameIdentifier, bacinoJson)
                                    };
                                }
                                else
                                {
                                    claims = new List<Claim>
                                    {
                                        new Claim(ClaimTypes.Gender, userI.id.ToString()),
                                        new Claim(ClaimTypes.Surname, userI.cognome),
                                        new Claim(ClaimTypes.Name, userI.nome),
                                        new Claim(ClaimTypes.Email, userI.mail),
                                        new Claim(ClaimTypes.UserData, userI.username),
                                        new Claim(ClaimTypes.Role, userI.role.ToString()),
                                        new Claim(ClaimTypes.Authentication, loginResponse.jwt)
                                    };
                                }

                                var claimsIdentity = new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

                                var authProperties = new AuthenticationProperties
                                {
                                    IsPersistent =
                                        false, // o false, a seconda se vuoi che la sessione persista tra le sessioni del browser
                                    ExpiresUtc =
                                        DateTimeOffset.UtcNow
                                            .AddMinutes(30) // imposta un tempo di scadenza del cookie, se necessario
                                };

                                await HttpContext.SignInAsync(IdentityConstants.ApplicationScheme,
                                    new ClaimsPrincipal(claimsIdentity),
                                    authProperties);
                            }
                        }
                        else
                        {
                            return Page();
                        }
                    }
                    catch (HttpRequestException e)
                    {
                        return Page();
                    }

                    return RedirectToPage("/Bacino/GestoreIdrico");
                case UserRole.SYSTEMADMIN:
                    return RedirectToPage("/Admin");
            }
        }
        else
        {
            Console.WriteLine("vuoto");
        }

        return Page();
    }
}