package Spring.WebApp.Transfer;

import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.Date;

@Service
public class TransferService {

    private final String url = "jdbc:mysql://localhost:3306/crud";
    private final String username = "root";
    private final String password = "14231568Z0a9!";

    public void withdrawAmount(String Username, double amount, String phone) {
        String sqlUpdateBalance = "UPDATE users SET balance = balance - ? WHERE username = ?";
        String sqlGetUserId = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Update user balance
            try (PreparedStatement stmtUpdateBalance = conn.prepareStatement(sqlUpdateBalance)) {
                stmtUpdateBalance.setDouble(1, amount);
                stmtUpdateBalance.setString(2, Username);
                int rowsAffected = stmtUpdateBalance.executeUpdate();
                if (rowsAffected == 1) {
                    // Retrieve user ID
                    try (PreparedStatement stmtGetUserId = conn.prepareStatement(sqlGetUserId)) {
                        stmtGetUserId.setString(1, Username);
                        ResultSet rs = stmtGetUserId.executeQuery();
                        if (rs.next()) {
                            int userId = rs.getInt("id");
                            addTransfer(Username, amount, phone, userId);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTransfer(String Username, double amount, String phone, int userId) {
        Date currentDate = new Date();
        String sql = "INSERT INTO transfers (user_id, amount, phone, transfer_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.setString(3, phone);
            stmt.setDate(4, new java.sql.Date(currentDate.getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
