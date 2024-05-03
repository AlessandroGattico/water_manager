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
        bool checkUser = false;
        bool checkMail = false;

        String cuser = JsonConvert.SerializeObject(registration.username);

        try
        {
            StringContent stringContent = new StringContent(cuser, Encoding.UTF8, "application/json");
            var response = await client.PostAsync("http://localhost:8080/api/v1/utils/check/username", stringContent);

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();
                checkUser = JsonConvert.DeserializeObject<bool>(responseContentStr);
            }
        }
        catch (Exception ex)
        {
            RedirectToPage("/Error/ServerOffline");
        }

        String cmail = JsonConvert.SerializeObject(registration.username);

        try
        {
            StringContent stringContent = new StringContent(cmail, Encoding.UTF8, "application/json");
            var response = await client.PostAsync("http://localhost:8080/api/v1/utils/check/email", stringContent);

            if (response.IsSuccessStatusCode)
            {
                string responseContentStr = await response.Content.ReadAsStringAsync();
                checkMail = JsonConvert.DeserializeObject<bool>(responseContentStr);
            }
        }
        catch (Exception ex)
        {
            RedirectToPage("/Error/ServerOffline");
        }

        if (checkUser && checkMail)
        {
            this.client = new HttpClient();

            String stringaDaInviare = JsonConvert.SerializeObject(registration);

            try
            {
                StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");
                var response = await client.PostAsync("http://localhost:8080/api/v1/auth/register", stringContent);

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
                RedirectToPage("/Error/ServerOffline");
            }

            if (loginResponse != null && !string.IsNullOrEmpty(loginResponse.jwt))
            {
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

                                    var claimsIdentity =
                                        new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

                                    var authProperties = new AuthenticationProperties
                                    {
                                        IsPersistent = false,
                                        ExpiresUtc = DateTimeOffset.UtcNow.AddMinutes(30)
                                    };

                                    await HttpContext.SignInAsync(IdentityConstants.ApplicationScheme,
                                        new ClaimsPrincipal(claimsIdentity), authProperties);
                                }
                            }
                            else
                            {
                                return Page();
                            }
                        }
                        catch (HttpRequestException e)
                        {
                            RedirectToPage("/Error/ServerOffline");
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


                                    var claimsIdentity =
                                        new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

                                    var authProperties = new AuthenticationProperties
                                    {
                                        IsPersistent = false,
                                        ExpiresUtc = DateTimeOffset.UtcNow.AddMinutes(30)
                                    };

                                    await HttpContext.SignInAsync(IdentityConstants.ApplicationScheme,
                                        new ClaimsPrincipal(claimsIdentity), authProperties);
                                }
                            }
                            else
                            {
                                return Page();
                            }
                        }
                        catch (HttpRequestException e)
                        {
                            RedirectToPage("/Error/ServerOffline");
                        }

                        return RedirectToPage("/Bacino/GestoreIdrico");
                    case UserRole.SYSTEMADMIN:
                        return RedirectToPage("/Error/UserNotLogged");
                }
            }
        }

        return Page();
    }
}