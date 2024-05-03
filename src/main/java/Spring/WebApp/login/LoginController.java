package Spring.WebApp.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("name")
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
	public String handleLogin(@RequestParam String name,
							  @RequestParam String password,
							  ModelMap model) {
		if(authenticationService.authenticate(name, password)) {
			model.addAttribute("name", name);
			return "welcome";
		}

		return "login";
	}
}
