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
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("login")
	public String handleLogin(@RequestParam String username,
							  @RequestParam String password,
							  ModelMap model) {
		if(authenticationService.authenticate(username, password)) {
			model.addAttribute("username", username);
			return "welcome";
		} else {
			model.addAttribute("msg", "Invalid username or password");
		}
		return "login";
	}
}
