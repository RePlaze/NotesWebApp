package Spring.WebApp.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final AuthenticationService authenticationService;

    public RegistrationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String handleRegistration(@RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String repeatPassword,
                                     ModelMap model) {
        // Check for empty fields
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            model.addAttribute("error", "All fields are required");
            return "registration";
        }

        // Check if password and repeat password match
        if (!password.equals(repeatPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "registration";
        }

        // Attempt to register the user
        boolean registrationResult = authenticationService.registerUser(username, password);
        if (registrationResult) {
            model.addAttribute("message", "Registration successful. You can now login.");

            return "login";
        } else {
            model.addAttribute("error", "Registration failed. Please try again later.");
            return "registration";
        }
    }
}
