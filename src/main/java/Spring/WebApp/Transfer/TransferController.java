package Spring.WebApp.Transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@Controller
@SessionAttributes("username")
public class TransferController {

    private final TransferService transferService;
    private String Username;
    private double localBalance;


    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("list-Transfers")
    public String listAllTransfers(ModelMap model) {
        String username = (String) model.get("username");
        Username = username;
        localBalance = getBalance(Username);
        model.addAttribute("username", username);
        model.addAttribute("balance", localBalance);
        return "listTransfers";
    }

    @PostMapping("withdrawAmount")
    public String withdrawAmount(@RequestParam("phone") String phone, @RequestParam("amount") double amount, ModelMap model) {
        transferService.withdrawAmount(Username, amount,phone);
        localBalance -= amount;
        model.addAttribute("balance", localBalance);
        return "redirect:/list-Transfers";
    }

    private Double getBalance(String username) {
        String sql = "SELECT balance FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud","root","14231568Z0a9!");
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
        return null;
    }
}
