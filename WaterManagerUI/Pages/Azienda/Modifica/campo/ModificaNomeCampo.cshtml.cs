using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using WaterManagerUI.Model.cambio;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Modifica.campo;

public class ModificaNomeCampo : PageModel
{
    [BindProperty] public String nomeNew { get; set; }
    public Campo campo { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private CambioString cambioNome { get; set; }


    public ModificaNomeCampo(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task OnGetAsync(int campoId)
    {
        var client = _httpClientFactory.CreateClient();

        try
        {
            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.GetAsync(
                $"http://localhost:8080/api/v1/azienda/campo/get/{campoId}");

            if (response.IsSuccessStatusCode)
            {
                var jsonResponse = await response.Content.ReadAsStringAsync();
                this.campo = JsonConvert.DeserializeObject<Campo>(jsonResponse);
            }
        }
        catch (Exception e)
        {
        }
    }

    public async Task<IActionResult> OnPostAsync(int campoId)
    {
        cambioNome = new CambioString(campoId, nomeNew, "nome");

        var client = _httpClientFactory.CreateClient();

        try
        {
            String stringaDaInviare = JsonConvert.SerializeObject(cambioNome);

            StringContent stringContent = new StringContent(stringaDaInviare, Encoding.UTF8, "application/json");

            client.DefaultRequestHeaders.Authorization =
                new AuthenticationHeaderValue("Bearer", User.FindFirstValue(ClaimTypes.Authentication));
            var response = await client.PostAsync(
                "http://localhost:8080/api/v1/azienda/campo/modifica/nome", stringContent);

            if (response.IsSuccessStatusCode)
            {
                var jsonResponse = await response.Content.ReadAsStringAsync();
                bool esito = JsonConvert.DeserializeObject<bool>(jsonResponse);

                if (esito)
                {
                }
            }
        }
        catch (Exception e)
        {
        }


        return RedirectToPage("/Azienda/Visualizza/campo/VisualizzaCampo", new { campoId = campoId });
    }
}