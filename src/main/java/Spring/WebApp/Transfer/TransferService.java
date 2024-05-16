package Spring.WebApp.Transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;

@Service
public class TransferService {

    private final Logger logger = LoggerFactory.getLogger(TransferService.class);

    private final String url = "jdbc:mysql://localhost:3306/crud";
    private final String username = "root";
    private final String password = "14231568Z0a9!";

    public void withdrawAmount(String username, double amount, String phone) throws SQLException {
        String sqlUpdateBalance = "UPDATE users SET balance = balance - ? WHERE username = ?";
        String sqlAddTransfer = "INSERT INTO transfers (user_id, amount, phone, transfer_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, this.username, password);
             PreparedStatement stmtUpdateBalance = conn.prepareStatement(sqlUpdateBalance);
             PreparedStatement stmtAddTransfer = conn.prepareStatement(sqlAddTransfer)) {
            conn.setAutoCommit(false);

            int userId = getUserId(username, conn);
            if (userId != -1) {
                stmtUpdateBalance.setDouble(1, amount);
                stmtUpdateBalance.setString(2, username);
                int rowsAffected = stmtUpdateBalance.executeUpdate();

                if (rowsAffected == 1) {
                    stmtAddTransfer.setInt(1, userId);
                    stmtAddTransfer.setDouble(2, amount);
                    stmtAddTransfer.setString(3, phone);
                    stmtAddTransfer.setDate(4, Date.valueOf(LocalDate.now()));
                    stmtAddTransfer.executeUpdate();

                    conn.commit();
                } else {
                    conn.rollback();
                    logger.error("Failed to update balance for user: {}", username);
                }
            } else {
                logger.error("User not found for username: {}", username);
            }
        }
    }

    private int getUserId(String username, Connection conn) throws SQLException {
        String sqlGetUserId = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlGetUserId)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    public double getBalance(String username) throws SQLException {
        String sql = "SELECT balance FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, this.username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        }
        return 0.0;
    }

    public double updateLocalBalance(String username, double amount) throws SQLException {
        return getBalance(username) - amount;
    }

    public boolean foundPhoneToTransfer(String phone) throws SQLException {
        String sql = "SELECT username FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSameUser(String username1, String username2) {
        return username1.equals(username2);
    }
}
