package Spring.WebApp.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("username")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("login")
    public String showLoginPage(@RequestParam(required = false) String msg, ModelMap model) {
        // Clear the error message when the page is loaded or updated
        model.addAttribute("error", "");
        // Set a flag to track form submission
        model.addAttribute("submitted", false);
        return "login";
    }

    @PostMapping("login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              ModelMap model) {
        // Set the form submission flag to true
        model.addAttribute("submitted", true);

        if (!authenticationService.authenticate(username, password)) {
            return "login";
        } else {
            model.addAttribute("username", username);
            return "redirect:list-Transfers";
        }
    }
}

