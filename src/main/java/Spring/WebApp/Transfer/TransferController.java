package Spring.WebApp.Transfer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;

@Controller
@SessionAttributes("username")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("list-Transfers")
    public String listAllTransfers(ModelMap model) {
        String username = (String) model.get("username");
        model.addAttribute("username", username);
        model.addAttribute("balance", getBalance(username));
        return "listTransfers";
    }

    // Show page to add a new Transfer
    @GetMapping("add-Transfer")
    public String showNewTransferPage(ModelMap model) {
        String username = (String) model.get("username");
        model.addAttribute("Transfer", new Transfer(0, username, "", LocalDate.now(), false));
        return "Transfer";
    }

    private String getBalance(String username) {
        String url = "jdbc:mysql://localhost:3306/crud";
        String user = "root";
        String pass = "14231568Z0a9!";
        String sql = "SELECT balance FROM users WHERE username = ?";
        int balance = 0;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                balance = rs.getInt("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(balance);
        return String.valueOf(balance);
    }

}
