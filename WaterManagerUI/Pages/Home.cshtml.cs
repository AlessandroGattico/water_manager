using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.ComponentModel.DataAnnotations;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Logging;
using System.Linq;
using System.Security.Claims;
using WaterManagerUI.Model.Security;

public class HomeModel : PageModel
{
    private readonly SignInManager<IdentityUser> _signInManager;
    private readonly UserManager<IdentityUser> _userManager;
    private readonly ILogger<HomeModel> _logger;
    public LoginRequestDTO loginRequestDTO;

    [BindProperty]
    public LoginInputModel Input { get; set; }
    public string ErrorMessage { get; set; }
    public string ReturnUrl { get; set; }
    public string ProviderDisplayName { get; set; }

    public HomeModel(SignInManager<IdentityUser> signInManager, UserManager<IdentityUser> userManager, ILogger<HomeModel> logger)
    {
        _signInManager = signInManager;
        _userManager = userManager;
        _logger = logger;
    }

    public class LoginInputModel
    {
        [Required]
        [EmailAddress]
        public string Email { get; set; }

        [Required]
        [DataType(DataType.Password)]
        public string Password { get; set; }
    }

    public void OnGet()
    {
    }

    public async Task<IActionResult> OnPostAsync()
    {
        if (ModelState.IsValid)
        {
            var result = await _signInManager.PasswordSignInAsync(Input.Email, Input.Password, isPersistent: false, lockoutOnFailure: false);
            if (result.Succeeded)
            {
                _logger.LogInformation("User logged in.");
                return RedirectToPage("/Index"); // O reindirizza alla pagina desiderata dopo il login
            }
            else
            {
                ModelState.AddModelError(string.Empty, "Login attempt failed.");
            }
        }

        // Se siamo arrivati fin qui, qualcosa è andato storto, mostra di nuovo il form
        return Page();
    }

    public async Task<IActionResult> OnPostExternalLoginAsync(string provider)
    {
        var redirectUrl = Url.Page("./Index", pageHandler: "Callback");
        var properties = _signInManager.ConfigureExternalAuthenticationProperties(provider, redirectUrl);
        return new ChallengeResult(provider, properties);
    }

    public async Task<IActionResult> OnGetCallbackAsync(string returnUrl = null, string remoteError = null)
    {
        if (remoteError != null)
        {
            ErrorMessage = $"Error from external provider: {remoteError}";
            return RedirectToPage("./Login");
        }
        var info = await _signInManager.GetExternalLoginInfoAsync();
        if (info == null)
        {
            ErrorMessage = "Error loading external login information.";
            return RedirectToPage("./Login");
        }

        // Sign in the user with this external login provider if the user already has a login.
        var result = await _signInManager.ExternalLoginSignInAsync(info.LoginProvider, info.ProviderKey, isPersistent: false, bypassTwoFactor: true);
        if (result.Succeeded)
        {
            _logger.LogInformation("{Name} logged in with {LoginProvider} provider.", info.Principal.FindFirstValue(ClaimTypes.Name), info.LoginProvider);
            return LocalRedirect(returnUrl ?? Url.Content("~/"));
        }
        if (result.IsLockedOut)
        {
            return RedirectToPage("./Lockout");
        }
        else
        {
            // If the user does not have an account, then ask the user to create an account.
            ReturnUrl = returnUrl;
            ProviderDisplayName = info.ProviderDisplayName;
            if (info.Principal.HasClaim(c => c.Type == ClaimTypes.Email))
            {
                Input = new LoginInputModel
                {
                    Email = info.Principal.FindFirstValue(ClaimTypes.Email)
                };
            }
            return Page();
        }
    }
}
