package Spring.WebApp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AuthenticationService {

	public boolean authenticate(String name, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/crud", "root", "14231568Z0a9!"
			);

			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM users WHERE username = ? AND password = ?");

			// Set parameters
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();

			// Check if the ResultSet contains any rows
			if (resultSet.next()) {
				System.out.println("Success");
				connection.close();
				return true;
			} else {
				System.out.println("Need registration");
			}

			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}

//@Service
//public class AuthenticationService {
//
//	private final UserRepository userRepository;
//	private final PasswordEncoder passwordEncoder;
//
//	@Autowired
//	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		this.userRepository = userRepository;
//		this.passwordEncoder = passwordEncoder;
//	}
//
//	public boolean authenticate(String username, String password) {
//		User user = userRepository.findByUsername(username);
//		return user != null && passwordEncoder.matches(password, user.getPassword());
//	}
//}
