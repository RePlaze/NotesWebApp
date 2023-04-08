package Spring.WebApp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	public boolean authenticate(String name, String password) {

		boolean isValidname = name.equalsIgnoreCase("nazenov");
		boolean isValidPassword = password.equalsIgnoreCase("pass123");

		return isValidname && isValidPassword;
	}
}
