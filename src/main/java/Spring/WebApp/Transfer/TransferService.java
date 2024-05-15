
package Spring.WebApp.Transfer;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;

@Service
public class TransferService {

    private final String url = "jdbc:mysql://localhost:3306/crud";
    private final String username = "root";
    private final String password = "14231568Z0a9!";

    public void withdrawAmount(String username, double amount, String phone) {
        String sqlUpdateBalance = "UPDATE users SET balance = balance - ? WHERE username = ?";
        String sqlGetUserId = "SELECT id FROM users WHERE username = ?";
        String sqlAddTransfer = "INSERT INTO transfers (user_id, amount, phone, transfer_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, this.username, password)) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtUpdateBalance = conn.prepareStatement(sqlUpdateBalance)) {
                stmtUpdateBalance.setDouble(1, amount);
                stmtUpdateBalance.setString(2, username);
                int rowsAffected = stmtUpdateBalance.executeUpdate();

                if (rowsAffected == 1) {
                    try (PreparedStatement stmtGetUserId = conn.prepareStatement(sqlGetUserId)) {
                        stmtGetUserId.setString(1, username);
                        ResultSet rs = stmtGetUserId.executeQuery();

                        if (rs.next()) {
                            int userId = rs.getInt("id");
                            try (PreparedStatement stmtAddTransfer = conn.prepareStatement(sqlAddTransfer)) {
                                stmtAddTransfer.setInt(1, userId);
                                stmtAddTransfer.setDouble(2, amount);
                                stmtAddTransfer.setString(3, phone);
                                stmtAddTransfer.setDate(4, Date.valueOf(LocalDate.now()));
                                stmtAddTransfer.executeUpdate();
                            }
                        }
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(String username) {
        String sql = "SELECT balance FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, this.username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double updateLocalBalance(String username, double amount) {
        return getBalance(username) - amount;
    }
}
