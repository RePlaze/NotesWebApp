package Spring.WebApp.Transfer;

import Spring.WebApp.Database.DBConnection;
import Spring.WebApp.Database.SQLQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransferService {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    public void withdrawAmount(String username, double amount, String phone) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmtUpdateSenderBalance = conn.prepareStatement(SQLQueries.UPDATE_SENDER_BALANCE);
             PreparedStatement stmtUpdateRecipientBalance = conn.prepareStatement(SQLQueries.UPDATE_RECIPIENT_BALANCE);
             PreparedStatement stmtAddTransfer = conn.prepareStatement(SQLQueries.ADD_TRANSFER)) {
            conn.setAutoCommit(false);
            int senderId = getUserId(username, conn);

            if (senderId != -1 && executeUpdate(stmtUpdateSenderBalance, amount, username) == 1) {
                executeUpdate(stmtUpdateRecipientBalance, amount, phone);
                executeTransfer(stmtAddTransfer, senderId, amount, phone);
                conn.commit();
            } else {
                conn.rollback();
                logger.error("Failed to perform withdrawal for user: {}", username);
            }
        }
    }
    public List<Transfer> getAllTransfersForUser(String username) throws SQLException {
        List<Transfer> transfers = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_TRANSFERS_FOR_USER)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transfers.add(mapResultSetToTransfer(rs));
                }
            }
        }
        Collections.reverse(transfers);

        return transfers;
    }

    private static int getUserId(String username, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USER_ID)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    public static int getUserId(String username) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SQLQueries.SELECT_USER_ID)) {
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
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_BALANCE)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        }
        return 0.0;
    }

    public boolean foundPhoneToTransfer(String phone) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USERNAME)) {
            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Transfer mapResultSetToTransfer(ResultSet rs) throws SQLException {
        String senderUsername = rs.getString("phone");
        double amount = rs.getDouble("amount");
        LocalDate date = rs.getDate("transfer_date").toLocalDate();
        return new Transfer(senderUsername, amount, date);
    }

    private int executeUpdate(PreparedStatement statement, double amount, String username) throws SQLException {
        statement.setDouble(1, amount);
        statement.setString(2, username);
        return statement.executeUpdate();
    }

    private void executeTransfer(PreparedStatement statement, int userId, double amount, String phone) throws SQLException {
        statement.setInt(1, userId);
        statement.setDouble(2, amount);
        statement.setString(3, phone);
        statement.setDate(4, Date.valueOf(LocalDate.now()));
        statement.executeUpdate();
    }

    public double updateLocalBalance(String username, double amount) throws SQLException {
        return getBalance(username) - amount;
    }

    public boolean isSameUser(String username1, String username2) {
        return username1.equals(username2);
    }
}
