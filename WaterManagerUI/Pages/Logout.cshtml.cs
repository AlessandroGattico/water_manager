using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace WaterManagerUI.Pages;

public class Logout : PageModel
{
    public async Task<IActionResult> OnPostAsync()
    {
        HttpContext.Session.Remove("UserSession");

        await HttpContext.SignOutAsync(IdentityConstants.ApplicationScheme);
        
        return RedirectToPage("/Index");
    }
}