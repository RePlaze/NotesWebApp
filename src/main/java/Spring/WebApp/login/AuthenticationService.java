package Spring.WebApp.login;

import Spring.WebApp.Database.DBConnection;
import Spring.WebApp.Database.SQLQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

import static Spring.WebApp.Transfer.TransferService.getUserId;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public boolean authenticate(String username, String password) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_USER)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean registerUser(String username, String password) throws SQLException {
        if (isEmpty(username) || isEmpty(password) || usernameExists(username)) {
            return false;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(SQLQueries.INSERT_USER);
             ) {

            // Insert new user into users table
            userStatement.setString(1, username);
            userStatement.setString(2, password);
            userStatement.setFloat(3, 1000);
            int userInserted = userStatement.executeUpdate();
            return true;
        }
    }


    public void addProfileId(String username) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_PROFILE)) {
            preparedStatement.setInt(1, getUserId(username));
            preparedStatement.setString(2, "Name");
            preparedStatement.setString(3, "test@gmail.com");
            preparedStatement.setString(4, "Other");
            preparedStatement.setDate(5, Date.valueOf("2000-01-01"));
            preparedStatement.executeUpdate();
        }
    }


    private boolean usernameExists(String username) throws SQLException {
        return executeExistsQuery(SQLQueries.SELECT_USERNAME, username);
    }

    private boolean executeExistsQuery(String sql, String parameter) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, parameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
