using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text.Json;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using WaterManagerUI.Data;

var builder = WebApplication.CreateBuilder(args);

var connectionString = builder.Configuration.GetConnectionString("DefaultConnection")
                       ?? throw new InvalidOperationException("Connection string 'DefaultConnection' not found.");

builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseSqlite(connectionString));

builder.Services.AddDatabaseDeveloperPageExceptionFilter();


builder.Services.AddAuthentication("cookie")
    .AddCookie("cookie")
    .AddOAuth("github", options =>
    {
        options.SignInScheme = "cookie";

        options.ClientId = builder.Configuration["GitHub:clientId"];
        options.ClientSecret = builder.Configuration["GitHub:clientSecret"];

        options.AuthorizationEndpoint = "https://github.com/login/oauth/authorize";
        options.TokenEndpoint = "https://github.com/login/oauth/access_token";
        options.CallbackPath = "/ExternalLogin";

        options.SaveTokens = true;
        options.UserInformationEndpoint = "https://api.github.com/user";

        options.ClaimActions.MapJsonKey(ClaimTypes.Actor, "id");
        options.ClaimActions.MapJsonKey(ClaimTypes.Name, "login");
        
        options.Events.OnCreatingTicket = async ctx =>
        {
            using var request = new HttpRequestMessage(HttpMethod.Get, ctx.Options.UserInformationEndpoint);
            request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", ctx.AccessToken);
            using var result = await ctx.Backchannel.SendAsync(request);
            var user = await result.Content.ReadFromJsonAsync<JsonElement>();
            ctx.RunClaimActions(user);
        };
    });

builder.Services.AddDefaultIdentity<IdentityUser>(options => options.SignIn.RequireConfirmedAccount = true)
    .AddEntityFrameworkStores<ApplicationDbContext>();

builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(30);
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});

builder.Services.AddDistributedMemoryCache();


builder.Services.AddRazorPages();
builder.Services.AddHttpClient();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseMigrationsEndPoint();
}
else
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.MapGet("/signin", () =>
{
    return Results.Challenge(new AuthenticationProperties()
        {
            RedirectUri = "https://localhost:5210/Index"
        }
        , authenticationSchemes: new List<string>() { "github" });
});

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseSession();

app.UseAuthentication();
app.UseAuthorization();

app.MapRazorPages();

app.Run();