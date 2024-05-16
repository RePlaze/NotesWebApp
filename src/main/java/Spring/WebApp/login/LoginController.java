package Spring.WebApp.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@SessionAttributes("username")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, ModelMap model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              ModelMap model) throws SQLException {
        if (authenticationService.authenticate(username, password)) {
            model.addAttribute("username", username);
            return "redirect:/list-Transfers";
        } else {
            return "redirect:/login?error=true";
        }
    }
}
