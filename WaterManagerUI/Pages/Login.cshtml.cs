using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using Serilog;
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

        Log.Information("Login user: " + login.username);

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
            RedirectToPage("/Error/ServerOffline");
        }

        if (loginResponse != null && !string.IsNullOrEmpty(loginResponse.jwt))
        {
            Log.Information("Login " + login.username + " succsessful");

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

                            Log.Information("User role " + userA.role);

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

                                var claimsIdentity = new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

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
                            Log.Information("Login denied");
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
                        var response = await client.GetAsync(
                            $"http://localhost:8080/api/v1/user/get/gi/{loginResponse.user.id}");

                        if (response.IsSuccessStatusCode)
                        {
                            var jsonResponse = await response.Content.ReadAsStringAsync();
                            userI = JsonConvert.DeserializeObject<GestoreIdrico>(jsonResponse);

                            Log.Information("User role " + userI.role);

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

                                var claimsIdentity = new ClaimsIdentity(claims, IdentityConstants.ApplicationScheme);

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
                            Log.Information("Login denied");
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
                            var response =
                                await client.GetAsync(
                                    $"http://localhost:8080/api/v1/admin/get/{loginResponse.user.id}");
                            
                            if (response.IsSuccessStatusCode)
                            {
                                var jsonResponse = await response.Content.ReadAsStringAsync();
                                admin = JsonConvert.DeserializeObject<UserInterfaceWaterManager.Model.User.Admin>(
                                    jsonResponse);
                                Log.Information("User role " + admin.role);


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
                                Log.Information("Login denied");
                                return Page();
                            }
                        }
                        catch (HttpRequestException e)
                        {
                            RedirectToPage("/Error/ServerOffline");
                        }
                    }

                    return RedirectToPage("/Admin/Admin");
            }
        }
        else
        {
            return Page();
        }


        return Page();
    }
}