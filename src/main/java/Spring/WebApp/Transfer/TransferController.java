package Spring.WebApp.Transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@SessionAttributes("username")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("list-Transfers")
    public String listAllTransfers(ModelMap model) throws SQLException {
        String username = (String) model.get("username");
        double balance = transferService.getBalance(username);
        model.addAttribute("username", username);
        model.addAttribute("balance", balance);
        return "listTransfers";
    }

    @PostMapping("withdrawAmount")
    public String withdrawAmount(@RequestParam("phone") String phone, @RequestParam("amount") double amount, ModelMap model) throws SQLException {
        String username = (String) model.get("username");
        double newBalance = transferService.updateLocalBalance(username, amount);

        // Check if the balance is sufficient for the transfer
        if (newBalance >= 0 && transferService.foundPhoneToTransfer(phone) && !transferService.isSameUser(username,phone)) {
            transferService.withdrawAmount(username, amount, phone);
            model.addAttribute("balance", newBalance);
            return "redirect:/list-Transfers";
        } else {
            model.addAttribute("error", "Insufficient balance to make this transfer");
            return "errorPage";
        }
    }
}
