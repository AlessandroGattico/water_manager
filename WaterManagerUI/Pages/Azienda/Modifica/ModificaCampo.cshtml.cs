using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using WaterManagerUI.Model.cambio;
using WaterManagerUI.Model.Item;

namespace WaterManagerUI.Pages.Modifica;

public class ModificaCampo : PageModel
{
    [BindProperty] public String nomeNew { get; set; }
    public Campo campo { get; set; }
    private readonly IHttpClientFactory _httpClientFactory;
    private CambioDouble cambioDim { get; set; }
    private CambioInt cambioCamp { get; set; }
    private CambioString cambioNome { get; set; }


    public ModificaCampo(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task<IActionResult> OnGetAsync(int campoId)
    {
        return RedirectToPage("/Azienda/Visualizza/VisualizzaCampo", campoId);
    }
}