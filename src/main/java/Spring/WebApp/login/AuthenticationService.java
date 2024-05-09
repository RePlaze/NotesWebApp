package Spring.WebApp.login;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AuthenticationService {

	public boolean authenticate(String username, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/crud", "root", "14231568Z0a9!"
			);

			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM users WHERE username = ? AND password = ?");

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				System.out.println("Success");
				connection.close();
				return true;
			} else {
				System.out.println("Invalid username or password");
			}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean registerUser(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			return false; // Empty fields
		}

		if (usernameExists(username)) {
			return false; // Username already exists
		}

		String url = "jdbc:mysql://localhost:3306/crud";
		String user = "root";
		String pass = "14231568Z0a9!";
		String sql = "INSERT INTO users (id,username, password) VALUES (?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url, user, pass);
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, String.valueOf(numOfUsers()));
			stmt.setString(2, username);
			stmt.setString(3, password);
			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private int numOfUsers() {
		String url = "jdbc:mysql://localhost:3306/crud";
		String user = "root";
		String pass = "14231568Z0a9!";
		String count = "SELECT COUNT(username) AS count FROM users";
		try (Connection conn = DriverManager.getConnection(url, user, pass);
			 Statement s = conn.createStatement();
			 ResultSet rs = s.executeQuery(count)) {
			if (rs.next()) {
				return rs.getInt("count");
			}
			return 0; // Return 0 if no users found
		} catch (SQLException e) {
			e.printStackTrace();
			return -1; // Return -1 to indicate error
		}
	}

	private boolean usernameExists(String username) {
		// Check if username exists in the database
		String url = "jdbc:mysql://localhost:3306/crud";
		String user = "root";
		String pass = "14231568Z0a9!";
		String sql = "SELECT username FROM users WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(url, user, pass);
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			return rs.next(); // Returns true if username exists in the database
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
