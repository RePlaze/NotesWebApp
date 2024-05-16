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
        String error = validateRegistration(username, password, repeatPassword);
        if (error != null) {
            model.addAttribute("error", error);
            return "registration";
        }

        boolean registrationResult = authenticationService.registerUser(username, password);
        if (registrationResult) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Registration failed. Please try again later.");
            return "registration";
        }
    }

    private String validateRegistration(String username, String password, String repeatPassword) {
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            return "All fields are required";
        }

        if (!password.equals(repeatPassword)) {
            return "Passwords do not match";
        }

        return null; // No error
    }
}