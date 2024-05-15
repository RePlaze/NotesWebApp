package Spring.WebApp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private static final String DB_URL = "jdbc:mysql://localhost:3306/crud";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "14231568Z0a9!";

    private static final String SELECT_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String INSERT_QUERY = "INSERT INTO users (username, password, balance) VALUES (?, ?, ?)";
    private static final String CHECK_USERNAME_QUERY = "SELECT username FROM users WHERE username = ?";

    // Authenticates a user by checking username and password in the database
    public boolean authenticate(String username, String password) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = prepareStatement(connection, SELECT_QUERY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("Error occurred while authenticating user", e);
            return false;
        }
    }

    // Registers a new user by inserting username, password, and default balance into the database
    public boolean registerUser(String username, String password) {
        if (isEmpty(username) || isEmpty(password) || usernameExists(username)) {
            return false;
        }
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = prepareStatement(connection, INSERT_QUERY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "1000");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while registering user", e);
            return false;
        }
    }

    // Checks if a username already exists in the database
    private boolean usernameExists(String username) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = prepareStatement(connection, CHECK_USERNAME_QUERY)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("Error occurred while checking username existence", e);
            return false;
        }
    }

    // Creates a prepared statement for the given SQL query
    private PreparedStatement prepareStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    // Establishes a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Checks if a string is null or empty
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
