using System.Diagnostics;
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

public class Login : PageModel
{
    [BindProperty] public LoginRequestDTO login { get; set; }
    public LoginResponseDTO? loginResponse { get; set; }
    private HttpClient client;
    private readonly IHttpContextAccessor _httpContextAccessor;

    public async Task<IActionResult> OnPostAsync()
    {
        if (!ModelState.IsValid)
        {
            return Page();
        }

        client = new HttpClient();

        String stringaDaInviare = JsonConvert.SerializeObject(login);

        try
        {
            StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
            var response = await client.PostAsync("http://localhost:8080/api/v1/auth/login", stringContent);

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();
                loginResponse = JsonConvert.DeserializeObject<LoginResponseDTO>(responseContentStr);
            }
            else
            {
                return Page();
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.StackTrace);
            return Page();
        }

        if (loginResponse != null && !string.IsNullOrEmpty(loginResponse.jwt))
        {
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
                                var claims = new List<Claim>
                                {
                                    new Claim(ClaimTypes.Name, userA.nome),
                                    new Claim(ClaimTypes.Email, userA.mail),
                                    new Claim(ClaimTypes.Role, userA.role.ToString()),
                                    new Claim(ClaimTypes.NameIdentifier, userA.azienda.id.ToString())
                                };

                                var claimsIdentity = new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

                                var authProperties = new AuthenticationProperties
                                {
                                    IsPersistent = false, // o false, a seconda se vuoi che la sessione persista tra le sessioni del browser
                                    ExpiresUtc =
                                        DateTimeOffset.UtcNow.AddMinutes(30) // imposta un tempo di scadenza del cookie, se necessario
                                };

                                await HttpContext.SignInAsync(IdentityConstants.ApplicationScheme, new ClaimsPrincipal(claimsIdentity),
                                    authProperties);
                                HttpContext.Session.SetString("UserSession", JsonConvert.SerializeObject(userA));
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

                    return RedirectToPage("/Azienda/GestoreAzienda");
                case UserRole.GESTOREIDRICO:
                    GestoreIdrico userI;

                    if (!string.IsNullOrEmpty(loginResponse.jwt))
                    {
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

                                HttpContext.Session.SetString("UserSession", JsonConvert.SerializeObject(userI));
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
                    }

                    return RedirectToPage("/Bacino/GestoreIdrico");
                case UserRole.SYSTEMADMIN:
                    Admin admin;

                    if (!string.IsNullOrEmpty(loginResponse.jwt))
                    {
                        try
                        {
                            client.DefaultRequestHeaders.Authorization =
                                new AuthenticationHeaderValue("Bearer", loginResponse.jwt);
                            var response =
                                await client.GetAsync(
                                    $"http://localhost:8080/api/v1/user/admin");
                            if (response.IsSuccessStatusCode)
                            {
                                var jsonResponse = await response.Content.ReadAsStringAsync();
                                admin = JsonConvert.DeserializeObject<Admin>(jsonResponse);

                                HttpContext.Session.SetString("UserSession", JsonConvert.SerializeObject(admin));
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
                    }

                    return RedirectToPage("/Admin");
            }
        }
        else
        {
            return Page();
        }


        return Page();
    }
}