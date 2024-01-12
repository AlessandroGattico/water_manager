using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using WaterManagerUI.Model.Security;

namespace WaterManagerUI.Pages;

public class IndexModel : PageModel
{
    private readonly ILogger<IndexModel> _logger;
    public LoginRequestDTO LoginRequestDTO;

    public IndexModel(ILogger<IndexModel> logger)
    {
        _logger = logger;
    }

    public void OnGet()
    {
    }
}