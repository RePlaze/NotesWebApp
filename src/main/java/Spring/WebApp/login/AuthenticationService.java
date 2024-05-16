package Spring.WebApp.login;

import Spring.WebApp.Database.DBConnection;
import Spring.WebApp.Database.SQLQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public boolean authenticate(String username, String password) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_USER)) {
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

    public boolean registerUser(String username, String password) {
        if (isEmpty(username) || isEmpty(password) || usernameExists(username)) {
            return false;
        }
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_USER)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "1000");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while registering user", e);
            return false;
        }
    }

    private boolean usernameExists(String username) {
        return executeExistsQuery(SQLQueries.SELECT_USERNAME, username);
    }

    private boolean executeExistsQuery(String sql, String parameter) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, parameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("Error occurred while executing query", e);
            return false;
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
