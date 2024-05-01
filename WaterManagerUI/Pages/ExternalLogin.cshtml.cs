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

public class ExternalLoginModel : PageModel
{
    private readonly IHttpClientFactory _httpClientFactory;

    public ExternalLoginModel(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public IActionResult OnGet(string provider, string returnUrl = null)
    {
        var redirectUrl = Url.Page("/signin", new { ReturnUrl = returnUrl });
        var properties = new AuthenticationProperties { RedirectUri = redirectUrl };
        return new ChallengeResult(provider, properties);
    }

    public async Task<IActionResult> OnGetCallbackAsync(string returnUrl = null, string remoteError = null)
    {
        if (remoteError != null)
        {
            return RedirectToPage("/Login");
        }

        var info = await HttpContext.AuthenticateAsync(IdentityConstants.ExternalScheme);
        if (info?.Principal != null)
        {
            var accessToken = info.Properties.GetTokenValue("access_token");
            var client = _httpClientFactory.CreateClient();
            try
            {
                var response = await client.PostAsync("http://localhost:8080/api/v1/auth/login/oauth2/code/github",
                    new StringContent(JsonConvert.SerializeObject(new { accessToken }), Encoding.UTF8,
                        "application/json"));

                if (response.IsSuccessStatusCode)
                {
                    var jsonResponse = await response.Content.ReadAsStringAsync();

                    LoginResponseDTO loginResponse = JsonConvert.DeserializeObject<LoginResponseDTO>(jsonResponse);

                    if (loginResponse.user.role == null)
                    {
                        return RedirectToPage("/OauthRegister", new { user = loginResponse.user });
                    }
                    else
                    {
                        switch (loginResponse.user.role)
                        {
                            case UserRole.GESTOREAZIENDA:
                                GestoreAzienda userA;

                                try
                                {
                                    client.DefaultRequestHeaders.Authorization =
                                        new AuthenticationHeaderValue("Bearer", loginResponse.jwt);
                                    var responseA = await client.GetAsync(
                                        $"http://localhost:8080/api/v1/user/get/ga/{loginResponse.user.id}");

                                    if (response.IsSuccessStatusCode)
                                    {
                                        var jsonResponseA = await responseA.Content.ReadAsStringAsync();
                                        userA = JsonConvert.DeserializeObject<GestoreAzienda>(jsonResponseA);


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
                                                new Claim(ClaimTypes.Authentication, loginResponse.jwt),
                                            };

                                            var claimsIdentity = new ClaimsIdentity(claims,
                                                IdentityConstants.ApplicationScheme);

                                            var authProperties = new AuthenticationProperties
                                            {
                                                IsPersistent = true,
                                                ExpiresUtc = DateTimeOffset.UtcNow.AddMinutes(30)
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

                                return RedirectToPage("/Azienda/GestoreAzienda");
                            case UserRole.GESTOREIDRICO:
                                GestoreIdrico userI;

                                try
                                {
                                    client.DefaultRequestHeaders.Authorization =
                                        new AuthenticationHeaderValue("Bearer", loginResponse.jwt);
                                    var responseI = await client.GetAsync(
                                        $"http://localhost:8080/api/v1/user/get/gi/{loginResponse.user.id}");

                                    if (response.IsSuccessStatusCode)
                                    {
                                        var jsonResponseI = await response.Content.ReadAsStringAsync();
                                        userI = JsonConvert.DeserializeObject<GestoreIdrico>(jsonResponseI);


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
                                                new Claim(ClaimTypes.Authentication, loginResponse.jwt),
                                            };


                                            var claimsIdentity = new ClaimsIdentity(claims,
                                                IdentityConstants.ApplicationScheme);

                                            var authProperties = new AuthenticationProperties
                                            {
                                                IsPersistent = true,
                                                ExpiresUtc = DateTimeOffset.UtcNow.AddMinutes(30)
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
                                UserInterfaceWaterManager.Model.User.Admin admin;

                                if (!string.IsNullOrEmpty(loginResponse.jwt))
                                {
                                    try
                                    {
                                        client.DefaultRequestHeaders.Authorization =
                                            new AuthenticationHeaderValue("Bearer", loginResponse.jwt);
                                        var responseAdmin =
                                            await client.GetAsync(
                                                $"http://localhost:8080/api/v1/admin/get/{loginResponse.user.id}");
                                        if (response.IsSuccessStatusCode)
                                        {
                                            var jsonResponseAdmin = await responseAdmin.Content.ReadAsStringAsync();
                                            admin = JsonConvert
                                                .DeserializeObject<UserInterfaceWaterManager.Model.User.Admin>(
                                                    jsonResponseAdmin);

                                            if (admin != null)
                                            {
                                                List<Claim> claims = new List<Claim>
                                                {
                                                    new Claim(ClaimTypes.Gender, admin.id.ToString()),
                                                    new Claim(ClaimTypes.Surname, admin.cognome),
                                                    new Claim(ClaimTypes.Name, admin.nome),
                                                    new Claim(ClaimTypes.Email, admin.mail),
                                                    new Claim(ClaimTypes.UserData, admin.username),
                                                    new Claim(ClaimTypes.Role, admin.role.ToString()),
                                                    new Claim(ClaimTypes.Authentication, loginResponse.jwt),
                                                };

                                                var claimsIdentity =
                                                    new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

                                                var authProperties = new AuthenticationProperties
                                                {
                                                    IsPersistent = false,
                                                    ExpiresUtc = DateTimeOffset.UtcNow.AddMinutes(30)
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
                                }

                                return RedirectToPage("/Admin/Admin");
                        }
                    }
                }
            }
            catch (Exception e)
            {
                RedirectToPage("/Error/ServerOffline");
            }
        }

        return RedirectToPage("/Login");
    }
}