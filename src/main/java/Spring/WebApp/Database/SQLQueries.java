package Spring.WebApp.Database;

public class SQLQueries {
    public static final String UPDATE_SENDER_BALANCE = "UPDATE users SET balance = balance - ? WHERE username = ?";
    public static final String UPDATE_RECIPIENT_BALANCE = "UPDATE users SET balance = balance + ? WHERE username = ?";
    public static final String ADD_TRANSFER = "INSERT INTO transfers (user_id, amount, phone, transfer_date) VALUES (?, ?, ?, ?)";
    public static final String SELECT_USER_ID = "SELECT id FROM users WHERE username = ?";
    public static final String SELECT_BALANCE = "SELECT balance FROM users WHERE username = ?";
    public static final String SELECT_USERNAME = "SELECT username FROM users WHERE username = ?";
    public static final String SELECT_USER = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String INSERT_USER = "INSERT INTO users (username, password, balance) VALUES (?, ?, ?)";
}

